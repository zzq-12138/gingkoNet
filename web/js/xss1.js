
//获取GET请求的参数
function getUrl(){
    var url = new URLSearchParams(location.search);
    return url.get(arguments[0]);
}


// //
// //限制字符类型
// document.getElementById('comment').addEventListener('input', function(event) {
//     var invalidChars = /[^\u4e00-\u9fa5a-zA-Z0-9\s.,!?，。]/g;   // 允许的字符类型
//     if (invalidChars.test(event.target.value)) {
//         event.target.value = event.target.value.replace(invalidChars, '');
//         alert('输入内容包含无效字符');
//     }
// });
//限制字符长度
document.getElementById('comment').addEventListener('input', function(event) {
    var maxLength = 400; // 设置最大长度
    if (event.target.value.length > maxLength) {
        event.target.value = event.target.value.substring(0, maxLength);
        alert('输入内容不能超过 ' + maxLength + ' 个字符');
    }
});

// function sanitizeInput(input) {
//     var patterns = ["<", ">", "script","/","&"];
//     patterns.forEach(function(pattern) {
//         var regex = new RegExp(pattern, 'gi');
//         input = input.replace(regex, ' ');
//     });
//     return input;
// }

function sanitize(input) {
    return input
}

var tips = sanitize(getUrl('xss'));
var xss = sanitize(getUrl('xss'));
//str模板字符串

var str = "";

//提示输入的内容，不执行xss
str += `<div align="center">`
str += `<br>你的建议：`;
str += `<span style="color:red;">`;
str += `{{tips}}`;
str += `</span><br><br>`;

//提示搜索结果，在这里执行xss
str += `对于问题：`;
str += `<span style="color:orange;">`;
str += `{{xss}}`;
str += `</span>我们会尽快解决`;
str += `</div>`;
