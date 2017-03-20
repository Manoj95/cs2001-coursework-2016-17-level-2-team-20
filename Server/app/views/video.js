var video = document.getElementById('video');
var source = document.createElement('source');

function playTimetable() {
  source.setAttribute('src', 'videos/compressed_timetable.mp4');
  video.appendChild(source);
  play()
}

function playMaps() {
  source.setAttribute('src', 'videos/compressed_maps.mp4');
  video.appendChild(source);
  play()
}

function play() {
  video.pause();
  video.load();
  video.play();
}
