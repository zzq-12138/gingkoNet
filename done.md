# Todo:网盘功能方面

##Todo重新登录的逻辑不对 done

应该先退出登录，然后跳转到登录页面，而不是直接跳转到登录页面

```
<li>
<a   href="/login.html" style="color: cadetblue;font-family: 'Comic Sans MS', cursive;font-size: 25px">重新登录</a>
</li>
```


##Todo:done

在controller将文件操作下载，删除，上传，展示等操作封装成一个类，方便调用

```
@WebServlet("/file/*")
class FileController{
    public function download(){
        //下载文件
    }
    public function delete(){
        //删除文件
    }
    public function upload(){
        //上传文件
    }
    public function show(){
        //展示文件
    }
}
```


##Todo:done
Bug: 上传文件时，没输入文件名也可以重命名点上传，会上传一个没有内容但是有名字的空文件

##Todo：done
BUG：上传文件时，文件名和已存在相同时会默认覆盖。应该提示是否覆盖，或者重命名

解决方法：在上传文件时，判断文件名是否存在，如果存在则向前端发送相应代码，由前端进行处理，提示是否覆盖，或者重命名

```
String realPath = req.getServletContext().getRealPath("/upload/" + userName + "/");
    File file = new File(realPath + fileName);
    if (file.exists()) {
        String overwrite = req.getParameter("overwrite");
        if ("true".equals(overwrite)) {
            // 用户选择覆盖文件，先删除原来的文件
            file.delete();
            // 保存新的文件
            myFile.write(realPath + "/" + fileName);
        } else {
            // 文件已经存在，返回一个特殊的响应
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }
    } else {
        // 如果文件不存在，直接保存新的文件
        myFile.write(realPath + "/" + fileName);
    }
```

``` 
function uploadFile() {
    var fileInput = document.getElementById('myFile');
    var uploadButton = document.getElementById('uploadButton');
    var newFileNameInput = document.getElementById('fileName'); // 获取重命名字段的输入框
    var formData = new FormData();
    formData.append('myFile', fileInput.files[0]);
    formData.append('fileName', newFileNameInput.value); // 将重命名字段的值添加到表单数据中

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/file/upload', true);
    xhr.onload = function () {
        if (xhr.status === 409) {
            var overwrite = confirm('文件已经存在，是否要覆盖文件？');
            if (overwrite) {
                formData.append('overwrite', 'true');
                xhr.open('POST', '/file/upload', true);
                xhr.send(formData);
            } else {
                var newFileName = prompt('请输入新的文件名：');
                formData.set('fileName', newFileName); // 更新表单数据中的文件名
                xhr.open('POST', '/file/upload', true);
                xhr.send(formData);
            }
        } else if (xhr.status === 200) {
            location.reload();
        } else {
            alert('上传文件失败：' + xhr.statusText);
        }
    };
    xhr.send(formData);
}
```

#Todo:done
Bug: 下载文件时，如果下载的是pdf格式的文件，会直接在浏览器中打开，而不是下载

解决方法：在下载文件时，设置响应头，告诉浏览器下载文件
由于浏览器的行为。一些浏览器，如Chrome，会尝试在浏览器中打开已知的文件类型，如PDF，
而不是下载它们。这是浏览器的默认行为，不是我的代码的问题。
所以我尝试一种方法来强制浏览器下载文件，而不是在浏览器中打开它。我在Content-Disposition头中添加filename参数，
这将告诉浏览器文件的名称，并提示浏览器下载文件，而不是在浏览器中打开它
```
resp.setContentType("application/octet-stream");
resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
```
在这个修改后的代码中，我在Content-Disposition头中添加了filename参数，并使用URLEncoder.encode方法对文件名进行了URL编码，
以确保文件名在HTTP头中正确传输。这将提示浏览器下载文件，而不是在浏览器中打开它。