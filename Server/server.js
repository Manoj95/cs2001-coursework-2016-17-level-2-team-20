var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var morgan = require('morgan');
var mongoose = require('mongoose');
var path = __dirname + '/views/';

// Used to create, sign, and verify tokens
var jwt = require('jsonwebtoken');

// Get our config file
var config = require('./config');

// Get our mongoose model
var Student = require('./app/models/student');

// Used to create, sign, and verify tokens
var port = process.env.PORT || 8080;

// Connect to database
mongoose.connect(config.database);

// Use body parser so we can get info from POST and/or URL parameters
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// Use morgan to log requests to the console
app.use(morgan('dev'));
app.use(express.static('views'))

app.get('/', function(req, res) {
    res.send('API @ http://localhost:' + port + '/api');
});

app.get('/brunelplanner', function(req, res) {
    res.sendFile(path + "index.html");
});

app.get('/setup', function(req, res) {

  var student = new Student({
    student: 1111111
  });

  student.save(function(err) {
    if (err) throw err;
    res.json({ success: true });
  });
});

var routes = express.Router();

routes.get('/', function(req, res) {
  res.json({ message: 'Notes API' });
});

routes.get('/students', function(req, res) {
  Student.find({}, function(err, students) {
    res.json(students);
  });
});

app.use('/api', routes);
app.listen(port);
