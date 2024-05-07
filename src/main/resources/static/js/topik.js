function renderDataTopikTable(data) {
    let tableHeader = "<table id='topik-table' className='table mt-3'><thead><tr><th>№</th><th>удалить</th><th>редактировать</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_topik_number_" + data[i].id + "\"><a href=\"/topiks/" + data[i].id + "\"><button class=\"btn btn-primary\">"+data[i].topikNumber+"</button></a></td>" +
            "<td id=\"tr_delete_topik_" + data[i].id + "\"><button class='btn btn-primary' onclick=\"deleteTopik(" + data[i].id + ")\">delete</button></td>" +
            "<td id=\"tr_edit_topik_" + data[i].id + "\"><button class='btn btn-primary' data-bs-toggle=\"modal\" data-bs-target=\"#topikModal\" onclick=\"editTopik(" + data[i].id + ")\">edit</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('topik-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","topik-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },1000);
}



function editTopik(user_id){
    document.getElementById('modal-topik').innerHTML = "РЕДАКТИРОВАТЬ TOPIK";

    let response = fetch("/topik/"  + user_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
                document.getElementById('id-topik-input').value=data.id;
                document.getElementById('topik-number-input').value=data.topikNumber;
            }
        )
        .catch(error => console.error('Error:', error));
}
function deleteTopik(user_id){
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/topik/" + user_id, {
        method: "DELETE",
        headers:header,
    }).then(response => response.json())
        .then(data =>{
            console.log(data);
            renderDataTopikTable(data);
        } )
        .catch(error => console.error('Error:', error));
}

const topikModal = document.getElementById('topikModal')
topikModal.addEventListener('hide.bs.modal', event => {

    document.getElementById('modal-topik').innerHTML = "ДОБАВИТЬ TOPIK";
    document.getElementById('id-topik-input').value="";
    document.getElementById('topik-number-input').value="";

})

function getFilePDF(file_id){
    console.log("df");
    let response = fetch("/file/downloadPDF/"  + file_id, {
        method: "GET",
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then( res => res.blob() )
        .then( blob => {
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
// the filename you want
            a.download = 'topik.pdf';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        });
}

function getFileAudio(file_id){
    let response = fetch("/file/downloadAudio/"  + file_id, {
        method: "GET",
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then( res => res.blob() )
        .then( blob => {
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
// the filename you want
            a.download = 'topik.mp3';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        });
}

