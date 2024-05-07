function renderDataLessonTable(data) {
    let tableHeader = "<table id='lesson-table' className='table mt-3'><thead><tr><th>№</th><th>Урок</th><th>удалить</th><th>редактировать</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_id_lesson_"+ data[i].id+"\">"+ data[i].id+"</td>" +
            "<td id=\"tr_title_lesson_" + data[i].id + "\"><a href=\"/lessons/"+data[i].id+"\"><button class=\"btn btn-primary\">"+data[i].title+"</button></a></td>"+
            "<td id=\"tr_delete_lesson_" + data[i].id + "\"><button class='btn btn-primary' onclick=\"deleteLesson(" + data[i].id + ")\">delete</button></td>" +
            "<td id=\"tr_edit_lesson_" + data[i].id + "\"><button class='btn btn-primary' data-bs-toggle=\"modal\" data-bs-target=\"#lessonModal\" onclick=\"editLesson(" + data[i].id + ")\">edit</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('lesson-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","lesson-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },2000);
}


function submitLessonForm() {
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    const formData = {
        id:document.getElementById('id-lesson-input').value,
        title: document.getElementById('title-lesson-input').value,
        content: document.getElementById('content-lesson').value,
        example: document.getElementById('example-lesson').value,
        dialog: document.getElementById('dialog-lesson').value,
        words: document.getElementById('words-lesson').value
    };

    let response = fetch("/lesson", {
        method: "POST", // *GET, POST, PUT, DELETE, etc.
        // mode: "cors", // no-cors, *cors, same-origin
        // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        // credentials: "same-origin", // include, *same-origin, omit
        headers: header,
        // redirect: "follow", // manual, *follow, error
        // referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(formData), // body data type must match "Content-Type" header
    }).then(response => response.json())
        .then(data => {
            document.getElementById('add-new-lesson-button').click();
            renderDataLessonTable(data);
            })
        .catch(error => console.error('Error:', error));


}

function editLesson(user_id){
    document.getElementById('modal-lesson').innerHTML = "РЕД. УРОК";

    let response = fetch("/lesson/"  + user_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
            console.log(data);
            document.getElementById('id-lesson-input').value=data.id;
            document.getElementById('title-lesson-input').value=data.title;
            document.getElementById('content-lesson').value=data.content;
            document.getElementById('example-lesson').value=data.example;
            document.getElementById('dialog-lesson').value=data.dialog;
            document.getElementById('words-lesson').value=data.words}
        )
        .catch(error => console.error('Error:', error));
}
function deleteLesson(user_id){
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/lesson/" + user_id, {
        method: "DELETE", // *GET, POST, PUT, DELETE, etc.
        // mode: "cors", // no-cors, *cors, same-origin
        // cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        // credentials: "same-origin", // include, *same-origin, omit
        headers: header,
        //     "Content-Type": "application/json;charset=UTF-8",
        //     // 'Content-Type': 'application/x-www-form-urlencoded',
        // },
        // redirect: "follow", // manual, *follow, error
        // referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        // body: JSON.stringify(user_id), // body data type must match "Content-Type" header
    }).then(response => response.json())
        .then(data => renderDataLessonTable(data))
        .catch(error => console.error('Error:', error));
}
// function openLesson(user_id){
//     let response = fetch("/lessons/" + user_id, {
//         method: "GET", // *GET, POST, PUT, DELETE, etc.
//     });
// }
const lessonModal = document.getElementById('lessonModal')
lessonModal.addEventListener('hide.bs.modal', event => {
    document.getElementById('modal-lesson').innerHTML = "ДОБАВИТЬ УРОК";
    document.getElementById('id-lesson-input').value="";
    document.getElementById('title-lesson-input').value="";
    document.getElementById('content-lesson').value="";
    document.getElementById('example-lesson').value="";
    document.getElementById('dialog-lesson').value="";
    document.getElementById('words-lesson').value="";
})

