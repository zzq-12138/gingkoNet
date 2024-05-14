// 校验用户名密码是否符合格式
function checkForm() {
    var flag1 = checkUsername();
    var flag2 = checkUserPwd();
    return flag1 && flag2;
}

// 校验用户名是否符合格式
function checkUsername() {
    // 定义正则表示字符串的规则,用户名必须以字母开头，长度在5-10之间,并且只能是字母数字下划线
    var usernameReg = /^[a-zA-Z]\w{4,9}$/;
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
    var  userPwdReg= /^[0-9]{6}$/;
    // 获得用户在页面上输入的信息
    var userPwdInput =document.getElementById("userPwdInput");
    var userPwd = userPwdInput.value;
    // 获得格式提示的框
    var userPwdMsg01 =document.getElementById("userPwdMsg01");
    var userPwdMsg02 =document.getElementById("userPwdMsg02");
    // 格式有误时,返回false,在页面上提示
    if(!userPwdReg.test(userPwd)){
        userPwdMsg01.innerText="提示:";
        userPwdMsg02.innerText="必须是6位数字";
        return false;
    }
    // 格式正确时,返回true，在页面上提示OK
    userPwdMsg01.innerText="";
    userPwdMsg02.innerText="OK";
    return true;

}