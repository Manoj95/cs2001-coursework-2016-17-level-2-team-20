// Get an instance of mongoose and mongoose.Schema
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var noteSchema = new Schema({
    date: { 
		type: Date, 
		required: true
	},
	title: { 
		type: String, 
		required: true,
		index: true
	},
    text: { 
		type: String, 
		required: true
	}
});

// Set up a mongoose model and pass it using module.exports
module.exports = mongoose.model('Student', new Schema({ 
	student: { 
		type: Number, 
		required: true,
		unique: true
	},
    notes: [noteSchema]
}));