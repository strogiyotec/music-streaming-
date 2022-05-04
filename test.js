function upload() {
  let file = document.getElementById("file").files[0];
  let formData = new FormData();
  formData.append("file", file);
  fetch('http://localhost:8080/file', {method: "POST", body: formData});
  fetchAll();
  alert("File was uploaded");
}

function deleteSong(elem) {
  var parentDiv = elem.parentNode;
  var audio = document.getElementsByTagName("audio")[0]
  var file = audio.getAttribute("id");
  fetch('http://localhost:8080/files/' + file, {
    method: 'DELETE'
  })
    .then(_ => {
      console.log("HERE");
      audio.remove();
    });
}

//fetch all songs
function fetchAll() {
  fetch('http://localhost:8080/files')
    .then(response => response.json())
    .then(data => {
      data.forEach(function (file) {
        const div = document.getElementById("songs");
        const sound = document.createElement('audio');
        sound.id = file;
        sound.controls = 'controls';
        sound.src = 'http://localhost:8080/files/' + file;
        sound.type = 'audio/mpeg';
        //div
        div.appendChild(sound);
        div.innerHTML += `<button  onclick="deleteSong(this)" >DELETE SONG</button>`;
        div.innerHTML += "<br>";
      });
    });
}

fetchAll();
