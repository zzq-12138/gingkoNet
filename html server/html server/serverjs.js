const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');
const fs = require('fs');
const path = require('path');
const session = require('express-session');
const app = express();
const PORT = 3000;
// 设置初始页面为Regist.html
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'Regist.html'));
  });

app.get('/register', (req, res) => {
    res.sendFile(path.join(__dirname, 'Regist.html'));
  });

app.get('/login', (req, res) => {
    res.sendFile(path.join(__dirname,'Login.html'));
  });
  app.get('/homepage', (req, res) => {
    console.log('Request URL:', req.url);
    res.sendFile(path.join(__dirname, 'Homepage.html'));
  });
// 设置静态文件目录
app.use(express.static('public'));

// 使用body-parser中间件
app.use(bodyParser.json());

// 设置multer存储引擎
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'public/uploadfiles/' + req.body.username);
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  }
});

const upload = multer({ storage: storage });

// 注册逻辑
app.post('/register', (req, res) => {
  const { username, password } = req.body;
  const usersFile = 'public/Users.json';

  fs.readFile(usersFile, 'utf8', (err, data) => {
    if (err) {
      if (err.code === 'ENOENT') {
        fs.writeFile(usersFile, JSON.stringify([]), (err) => {
          if (err) throw err;
        });
      } else {
        throw err;
      }
    } else {
      const users = JSON.parse(data);
      const existingUser = users.find(user => user.username === username);
      if (existingUser) {
        return res.status(409).send('用户名已存在');
      }
      users.push({ username, password });
      fs.writeFile(usersFile, JSON.stringify(users), (err) => {
        if (err) throw err;
      });
    }
  });

  res.send('注册成功');
});

// 设置session中间件
app.use(session({
    secret: 'your-secret-key',
    resave: false,
    saveUninitialized: true
  }));
  
  // 登录逻辑
  app.post('/login', (req, res) => {
    const { username, password } = req.body;
    const usersFile = 'public/Users.json';
  
    fs.readFile(usersFile, 'utf8', (err, data) => {
      if (err) throw err;
      const users = JSON.parse(data);
      const user = users.find(user => user.username === username && user.password === password);
      if (user) {
        req.session.username = username; // 设置会话中的用户名
        res.send('登录成功');
      } else {
        res.status(401).send('用户名或密码错误');
      }
    });
  });

// 上传文件逻辑
app.post('/upload', upload.single('file'), (req, res) => {
  const { username } = req.body;
  const file = req.file;
  const totalFileDataFile = 'public/TotalData/TotalFileData.json';
  const userFileDataFile = 'public/uploadfiles/' + username + '/fileData/Filedata.json';

  const fileInfo = {
    filename: file.originalname,
    uploadDate: new Date().toISOString(),
    fileSize: file.size,
    fileExtension: path.extname(file.originalname)
  };

  // 更新TotalFileData.json
  fs.readFile(totalFileDataFile, 'utf8', (err, data) => {
    if (err) {
      if (err.code === 'ENOENT') {
        fs.writeFile(totalFileDataFile, JSON.stringify([]), (err) => {
          if (err) throw err;
        });
      } else {
        throw err;
      }
    } else {
      const totalFileData = JSON.parse(data);
      totalFileData.push(fileInfo);
      fs.writeFile(totalFileDataFile, JSON.stringify(totalFileData), (err) => {
        if (err) throw err;
      });
    }
  });

  // 更新用户的fileData.json
  fs.readFile(userFileDataFile, 'utf8', (err, data) => {
    if (err) {
      if (err.code === 'ENOENT') {
        fs.writeFile(userFileDataFile, JSON.stringify([]), (err) => {
          if (err) throw err;
        });
      } else {
        throw err;
      }
    } else {
      const userFileData = JSON.parse(data);
      userFileData.push(fileInfo);
      fs.writeFile(userFileDataFile, JSON.stringify(userFileData), (err) => {
        if (err) throw err;
      });
    }
  });

  res.send('文件上传成功');
});

// 删除文件逻辑
app.post('/delete', (req, res) => {
  const { username, filename } = req.body;
  const userFileDataFile = 'public/uploadfiles/' + username + '/fileData/Filedata.json';
  const totalFileDataFile = 'public/TotalData/TotalFileData.json';
  const filePath = 'public/uploadfiles/' + username + '/' + filename;

  // 从用户的fileData.json中删除
  fs.readFile(userFileDataFile, 'utf8', (err, data) => {
    if (err) throw err;
    const userFileData = JSON.parse(data);
    const updatedUserFileData = userFileData.filter(file => file.filename !== filename);
    fs.writeFile(userFileDataFile, JSON.stringify(updatedUserFileData), (err) => {
      if (err) throw err;
    });
  });

  // 从TotalFileData.json中删除
  fs.readFile(totalFileDataFile, 'utf8', (err, data) => {
    if (err) throw err;
    const totalFileData = JSON.parse(data);``
    const updatedTotalFileData = totalFileData.filter(file => file.filename !== filename);
    fs.writeFile(totalFileDataFile, JSON.stringify(updatedTotalFileData), (err) => {
      if (err) throw err;
    });
  });

  // 删除文件
  fs.unlink(filePath, (err) => {
    if (err) throw err;
  });

  res.send('文件删除成功');
});

app.listen(PORT, () => {
  console.log('服务器正在运行在 http://localhost:3000');
});
// 获取用户文件数据逻辑
app.get('/uploadfiles/:username/fileData/Filedata.json', (req, res) => {
    const username = req.params.username;
    const userFileDataFile = 'public/uploadfiles/' + username + '/fileData/Filedata.json';
  
    fs.readFile(userFileDataFile, 'utf8', (err, data) => {
      if (err) {
        if (err.code === 'ENOENT') {
          res.status(404).send('用户文件数据不存在');
        } else {
          throw err;
        }
      } else {
        res.json(JSON.parse(data));
      }
    });
  });

  
