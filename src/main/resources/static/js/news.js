function renderDataNewsTable(data) {
    let tableHeader = "<table id='news-table' className='table mt-3'><thead><tr><th>№</th><th>заголовок</th><th>удалить</th><th>редактировать</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_id_news_"+ data[i].id+"\">"+ data[i].id+"</td>" +
            "<td id=\"tr_title_news_" + data[i].id + "\"><a href=\"/newses/" + data[i].id + "\"><button class=\"btn btn-primary\">"+data[i].title+"</button></a></td>" +
            "<td id=\"tr_delete_news_" + data[i].id + "\"><button class='btn btn-primary' onclick=\"deleteNews(" + data[i].id + ")\">delete</button></td>" +
            "<td id=\"tr_edit_news_" + data[i].id + "\"><button class='btn btn-primary' data-bs-toggle=\"modal\" data-bs-target=\"#newsModal\" onclick=\"editNews(" + data[i].id + ")\">edit</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('news-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","news-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },1000);
}


// function submitNewsForm() {
//     const formData = {
//         // id:"02",
//         // title:document.getElementById('title-news-input').value,
//         // content: document.getElementById('content-news').value,
//         title:document.getElementById('title-news-input').value,
//         picture: document.getElementById('file-news').files[0],
//     };
//     let id="02";
//     let   title=document.getElementById('title-news-input').value;
//     let picture=document.getElementById('file-news').files[0];
//         // content: document.getElementById('content-news').value;
//         // description: document.getElementById('description-news').value;
//     console.log(formData)
//     let response = fetch("/news",{
//         method: "POST",
//         headers: {
//             // "Content-Type": "application/json;charset=UTF-8",
//             'Content-Type': 'multipart/form-data;charset=UTF-8"',
//         },
//         body: JSON.stringify(formData),
//     }).then(response => response.json())
//         .then(data => renderDataGrammarTable(data))
//         .catch(error => console.error('Error:', error));
// }

function editNews(user_id){
    document.getElementById('modal-news').innerHTML = "РЕДАКТИРОВАТЬ НОВОСТЬ";

    let response = fetch("/news/"  + user_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
                document.getElementById('id-news-input').value=data.id;
                document.getElementById('title-news-input').value=data.title;
                document.getElementById('content-news').value=data.content;
                document.getElementById('description-news').value=data.description;
            }
        )
        .catch(error => console.error('Error:', error));
}
function deleteNews(user_id){
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/news/" + user_id, {
        method: "DELETE",
        headers:header,
    }).then(response => response.json())
        .then(data => renderDataNewsTable(data))
        .catch(error => console.error('Error:', error));
}
// function openLesson(user_id){
//     let response = fetch("/lessons/" + user_id, {
//         method: "GET", // *GET, POST, PUT, DELETE, etc.
//     });
// }

const newsModal = document.getElementById('newsModal')
newsModal.addEventListener('hide.bs.modal', event => {

    document.getElementById('modal-news').innerHTML = "ДОБАВИТЬ НОВОСТЬ";
    document.getElementById('id-news-input').value="";
    document.getElementById('title-news-input').value="";
    document.getElementById('content-news').value="";
    document.getElementById('description-news').value="";
})