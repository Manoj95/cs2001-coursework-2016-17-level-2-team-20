var video = document.getElementById('video');
var source = document.createElement('source');

function playTimetable() {
  hideAll();
  $( ".timetable" ).toggle();
  source.setAttribute('src', 'videos/compressed_timetable.mp4');
  video.appendChild(source);
  play()
}

function playMaps() {
  hideAll();
  $( ".maps" ).toggle();
  source.setAttribute('src', 'videos/compressed_maps.mp4');
  video.appendChild(source);
  play()
}

function playEvents() {
  hideAll();
  $( ".event" ).toggle();
  source.setAttribute('src', 'videos/compressed_event.mp4');
  video.appendChild(source);
  play()
}

function playNotes() {
  hideAll();
  $( ".note" ).toggle();
  source.setAttribute('src', 'videos/compressed_note.mp4');
  video.appendChild(source);
  play()
}

function play() {
  video.pause();
  video.load();
  video.play();
}

function hideAll() {
  $( ".timetable" ).toggle(false);
  $( ".maps" ).toggle(false);
  $( ".event" ).toggle(false); 
  $( ".note" ).toggle(false); 
}