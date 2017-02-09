var express     = require('express');
var app         = express();
var bodyParser  = require('body-parser');
var morgan      = require('morgan');
var mongoose    = require('mongoose');

// used to create, sign, and verify tokens
var jwt    = require('jsonwebtoken');
// get our config file
var config = require('./config');
// get our mongoose model
var User   = require('./app/models/student');
// used to create, sign, and verify tokens
var port = process.env.PORT || 8080;
 // connect to database
mongoose.connect(config.database);
// use body parser so we can get info from POST and/or URL parameters
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// use morgan to log requests to the console
app.use(morgan('dev'));

// basic route
app.get('/', function(req, res) {
    res.send('Hello! The API is at http://localhost:' + port + '/api');
});


app.listen(port);
console.log('Magic happens at http://localhost:' + port);