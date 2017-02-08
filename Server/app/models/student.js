var mongoose = require('mongoose');
var Schema = mongoose.Schema;

module.exports = mongoose.model('Student', new Schema({ 
    student: String
}));