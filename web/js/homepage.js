window.onload = function() {
    checkLoginStatus();
};

function checkLoginStatus() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/schedule_system/user/checkLogin', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            if (!response.loggedIn) {
                showLoginModal();
                document.getElementById('logoutButton').style.display = 'none';
            }
        }
    };
    xhr.send();
}

function showLoginModal() {
    var loginModal = document.getElementById('loginModal');
    loginModal.style.display = 'block';
    disableOtherActions(true);
}

function disableOtherActions(disable) {
    var elements = document.querySelectorAll('input, button, a');
    elements.forEach(function(element) {
        if (element.id !== 'loginModal' && element.id !== 'cancelButton') {
            element.disabled = disable;
        }
    });
}

function checkFile() {
    var fileInput = document.getElementById('myFile');
    var uploadButton = document.getElementById('uploadButton');
    if (fileInput.value == '') {
        uploadButton.disabled = true;
    } else {
        uploadButton.disabled = false;
    }
}

function checkFileName(fileName) {
    // 验证文件名是否合格
    if (fileName.includes("..") || fileName.startsWith(".")) {
        alert("文件名不合格，请重新上传");
        return false;
    }
    return true;
}

function uploadFile() {
    var fileInput = document.getElementById('myFile');
    var uploadButton = document.getElementById('uploadButton');
    var newFileNameInput = document.getElementById('fileName'); // 获取重命名字段的输入框
    var progressModal = document.getElementById('progressModal');
    var progressBar = document.getElementById('uploadProgress');
    var uploadStatus = document.getElementById('uploadStatus');
    var cancelButton = document.getElementById('cancelButton');
    var fileName = fileInput.files[0].name;
    var newFileName = document.getElementById('fileName').value;
    if (newFileName) {
        fileName = newFileName;
    }

    if (!checkFileName(fileName)) {
        return;
    }

    var formData = new FormData();
    formData.append('myFile', fileInput.files[0]);
    formData.append('fileName', newFileNameInput.value); // 将重命名字段的值添加到表单数据中

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/schedule_system/file/upload', true);

    xhr.upload.onprogress = function (event) {
        if (event.lengthComputable) {
            var percentComplete = (event.loaded / event.total) * 100;
            progressBar.value = percentComplete;
            uploadStatus.textContent = `上传进度: ${percentComplete.toFixed(2)}%`;
        }
    };

    xhr.onloadstart = function () {
        uploadButton.disabled = true;
        uploadButton.style.backgroundColor = 'gray';
        progressModal.style.display = 'block';
        disableOtherActions(true);
    };

    xhr.onload = function () {
        if (xhr.status === 409) {
            var overwrite = confirm('文件已经存在，是否要覆盖文件？');
            if (overwrite) {
                formData.append('overwrite', 'true');
                xhr.open('POST', '/schedule_system/file/upload', true);
                xhr.send(formData);
            } else {
                var newFileName = prompt('请输入新的文件名：');
                formData.set('fileName', newFileName); // 更新表单数据中的文件名
                xhr.open('POST', '/schedule_system/file/upload', true);
                xhr.send(formData);
            }
        } else if (xhr.status === 200) {
            location.reload();
        } else {
            alert('上传文件失败：' + xhr.statusText);
            uploadButton.disabled = false;
            uploadButton.style.backgroundColor = '';
            progressModal.style.display = 'none';
            disableOtherActions(false);
        }
    };
    xhr.send(formData);
}