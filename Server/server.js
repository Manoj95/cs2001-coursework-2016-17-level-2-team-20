var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var morgan = require('morgan');
var mongoose = require('mongoose');
var passport = require('passport');
var jwt = require('jsonwebtoken');
var config = require('./config/config');
var port = process.env.PORT || 8080;

// Webpage
app.use(express.static('app/views'))

// Get our mongoose model
var Student = require('./app/models/student');

// Connect to database
mongoose.connect(config.database);
app.use(passport.initialize())
require('./config/passport')(passport);

// Use body parser so we can get info from POST and/or URL parameters
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// Use morgan to log requests to the console
app.use(morgan('dev'));

app.get('/', function(req, res) {
  res.send('API @ http://localhost:' + port + '/api');
});

app.get('/brunelplanner', function(req, res) {
  res.sendFile(__dirname + '/app/views/index.html');
});

var routes = express.Router();

routes.get('/', function(req, res) {
  res.json({ message: 'Notes API' });
});

routes.post('/register', function(req, res) {
  if(!req.body.student || !req.body.password) {
    res.json({ success: false, message: 'Please enter student id and password.' });
  } else {
    var newStudent = new Student({
      student: req.body.student,
      password: req.body.password
    });

    // Attempt to save the user
    newStudent.save(function(err) {
      if (err) {
        return res.json({ success: false, message: 'That student already exists.'});
      }
      res.json({ success: true, message: 'Successfully created new student.' });
    });
  }
});

routes.post('/auth', function(req, res) {
  Student.findOne({
    student: req.body.student
  }, function(err, user) {
    if (err) throw err;

    if (!user) {
      res.send({ success: false, message: 'Authentication failed. Student not found.' });
    } else {
      // Check if password matches
      user.comparePassword(req.body.password, function(err, isMatch) {
        if (isMatch && !err) {
          // Create token if the password matched and no error was thrown
          var token = jwt.sign(user, config.secret, {
            expiresIn: 10080 // in seconds
          });
          res.json({ success: true, token: 'JWT ' + token });
        } else {
          res.send({ success: false, message: 'Authentication failed. Passwords did not match.' });
        }
      });
    }
  });
});

routes.get('/students', function(req, res) {
  Student.getStudents(function(err, students) {
    if (err) throw err;
    res.json(students);
  });
});

routes.get('/notes', function(req, res) {
  Student.getStudents(function(err, students) {
    if (err) throw err;
    res.json(students);
  });
});

routes.get('/auth', function(req, res) {
  // ...
});

app.use('/api', routes);
app.listen(port);
