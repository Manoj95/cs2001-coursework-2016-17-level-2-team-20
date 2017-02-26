var mongoose = require('mongoose');
var bcrypt = require('bcrypt');
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

var studentSchema = new Schema({
  student: {
    type: Number,
    required: true,
    unique: true
  },
  password: {
    type: String,
    required: true
  },
  notes: [noteSchema]
});

//
// // Set up a mongoose model and pass it using module.exports
// module.exports = mongoose.model('Student', new Schema({
// 	student: {
// 		type: Number,
// 		required: true,
// 		unique: true
// 	},
//     notes: [noteSchema]
// }));

// Saves the user's password hashed (plain text password storage is not good)
studentSchema.pre('save', function (next) {
  var user = this;
  if (this.isModified('password') || this.isNew) {
    bcrypt.genSalt(10, function (err, salt) {
      if (err) {
        return next(err);
      }
      bcrypt.hash(user.password, salt, function(err, hash) {
        if (err) {
          return next(err);
        }
        user.password = hash;
        next();
      });
    });
  } else {
    return next();
  }
});

studentSchema.methods.comparePassword = function(password, callback) {
  bcrypt.compare(password, this.password, function(err, isMatch) {
    if (err) {
      return callback(err);
    }
    callback(null, isMatch);
  });
};

var Student = module.exports = mongoose.model('Student', studentSchema);

module.exports.getStudents = function(callback, limit) {
  Student.find(callback).limit(limit);
}

module.exports.getNotes = function(callback, limit) {
  Student.find(callback).limit(limit);
}
