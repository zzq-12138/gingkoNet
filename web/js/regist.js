// 校验用户名密码是否符合格式
function checkForm() {
    var flag1 = checkUsername();
    var flag2 = checkUserPwd();
    return flag1 && flag2;
}

// 校验用户名是否符合格式
function checkUsername() {
    // 定义正则表示字符串的规则,用户名必须以字母开头，长度在5-16之间,并且只能是字母数字下划线
    var usernameReg = /^[a-zA-Z]\w{4,15}$/;
    // 获得用户输入的信息
    var usernameInput = document.getElementById("usernameInput");
    var username = usernameInput.value;
    // 获得格式提示的框
    var usernameMsg01 = document.getElementById("usernameMsg01");
    var usernameMsg02 = document.getElementById("usernameMsg02");
    // 格式有误返回false，在页面上提示
    if(!usernameReg.test(username)){
        usernameMsg01.innerHTML = "提示:";
        usernameMsg02.innerHTML = "必须以字母开头且长度在5-10之间";
        return false;
    }
    // 格式正确时,返回true，在页面上提示OK
    usernameMsg01.innerText="";
    usernameMsg02.innerText="OK";
    return true;
}

// 校验密码是否符合格式
function checkUserPwd(){
    // 定义正则表示字符串的规则
    var userPwdReg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*."=']).{8,16}$/;
    var userPwdInput =document.getElementById("userPwdInput");
    var userPwd = userPwdInput.value;
    // 获得格式提示的框
    var userPwdMsg01 =document.getElementById("userPwdMsg01");
    var userPwdMsg02 =document.getElementById("userPwdMsg02");
    // 格式有误时,返回false,在页面上提示
    if(!userPwdReg.test(userPwd)){
        userPwdMsg01.innerText="提示:";
        userPwdMsg02.innerText="密码必须是8-16位的数字、字母和合法特殊符号的组合";
        return false;
    }
    // 格式正确时,返回true，在页面上提示OK
    userPwdMsg01.innerText="";
    userPwdMsg02.innerText="OK";
    return true;

}

//校验两次密码是否一致
function reCheckReUserPwd() {
    var userPwdInput = document.getElementById("userPwdInput");
    var userPwd = userPwdInput.value;
    var userPwd2Input = document.getElementById("reUserPwdInput");
    var userPwd2 = userPwd2Input.value;
    var reUserPwdMsg01 = document.getElementById("reUserPwdMsg01");
    var reUserPwdMsg02 = document.getElementById("reUserPwdMsg02");
    if (userPwd !== userPwd2) {
        reUserPwdMsg01.innerText = "提示:";
        reUserPwdMsg02.innerText = "两次密码不一致";
        return false;
    }
    reUserPwdMsg01.innerText = "";
    reUserPwdMsg02.innerText = "OK";
    return true;
}

//鼠标在登录按钮上悬停时，改变按钮的颜色,鼠标移开时恢复颜色,鼠标变为手型
function changeBtnColor() {
    var Btn = document.getElementById("Btn");
    Btn.style.backgroundColor = "#cc00ff";
    Btn.style.cursor = "pointer";
}

//鼠标移开时恢复按钮颜色
function recoverBtnColor() {
    var Btn = document.getElementById("Btn");
    Btn.style.backgroundColor = "#ff99ff";
}

function changeBtn1Color(){
    var Btn1 = document.getElementById("Btn1");
    Btn1.style.backgroundColor = "#cc00ff";
    Btn1.style.cursor = "pointer";
}

function recoverBtn1Color(){
    var Btn1 = document.getElementById("Btn1");
    Btn1.style.backgroundColor = "#ff99ff";
}

function changeBtn2Color(){
    var Btn2 = document.getElementById("Btn2");
    Btn2.style.backgroundColor = "#cc00ff";
    Btn2.style.cursor = "pointer";
}

function recoverBtn2Color(){
    var Btn2 = document.getElementById("Btn2");
    Btn2.style.backgroundColor = "#ff99ff";
}

function checkPasswordMatch() {
    var password = document.getElementById('userPwdInput').value;
    var confirmPassword = document.getElementById('reUserPwd').value;
    var registerButton = document.getElementById('Btn');

    if (password !== confirmPassword) {
        registerButton.disabled = true;
        document.getElementById('finalErrorMsg').style.display = 'block';
    } else {
        registerButton.disabled = false;
        document.getElementById('finalErrorMsg').style.display = 'none';
    }
}

// 添加事件监听器
document.getElementById("userPwdInput").addEventListener("blur", checkUserPwd);