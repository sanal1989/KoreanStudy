function renderDataGrammarTable(data) {
    let tableHeader = "<table id='grammar-table' className='table mt-3'><thead><tr><th>№</th><th>Грамматика</th><th>удалить</th><th>редактировать</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_id_grammar_"+ data[i].id+"\">"+ data[i].id+"</td>" +
            "<td id=\"tr_title_grammar_" + data[i].id + "\"><a href=\"/grammars/" + data[i].id + "\"><button class=\"btn btn-primary\">"+data[i].title+"</button></a></td>" +
            "<td id=\"tr_delete_grammar_" + data[i].id + "\"><button class='btn btn-primary' onclick=\"deleteGrammar(" + data[i].id + ")\">delete</button></td>" +
            "<td id=\"tr_edit_grammar_" + data[i].id + "\"><button class='btn btn-primary' data-bs-toggle=\"modal\" data-bs-target=\"#grammarModal\" onclick=\"editGrammar(" + data[i].id + ")\">edit</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('grammar-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","grammar-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },1000);
}


function submitGrammarForm() {
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    const formData = {
        id:document.getElementById('id-grammar-input').value,
        title: document.getElementById('title-grammar-input').value,
        content: document.getElementById('content-grammar').value,
        example: document.getElementById('example-grammar').value,
    };
    console.log(formData)
    let response = fetch("/grammar", {
        method: "POST",
        headers: header,
        body: JSON.stringify(formData),
    }).then(response => response.json())
        .then(data =>{
            document.getElementById('add-new-grammar-button').click();
            renderDataGrammarTable(data);
        } )
        .catch(error => console.error('Error:', error));
}

function editGrammar(user_id){
    document.getElementById('modal-grammar').innerHTML = "РЕД. ГРАММАТИКУ";

    let response = fetch("/grammar/"  + user_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
            console.log(data);
            document.getElementById('id-grammar-input').value=data.id;
            document.getElementById('title-grammar-input').value=data.title;
            document.getElementById('content-grammar').value=data.content;
            document.getElementById('example-grammar').value=data.example;
            }
        )
        .catch(error => console.error('Error:', error));
}
function deleteGrammar(user_id){
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/grammar/" + user_id, {
        method: "DELETE",
        headers:header,
    }).then(response => response.json())
        .then(data => renderDataGrammarTable(data))
        .catch(error => console.error('Error:', error));
}
// function openLesson(user_id){
//     let response = fetch("/lessons/" + user_id, {
//         method: "GET", // *GET, POST, PUT, DELETE, etc.
//     });
// }
const grammarModal = document.getElementById('grammarModal')
grammarModal.addEventListener('hide.bs.modal', event => {

    document.getElementById('modal-grammar').innerHTML = "ДОБАВИТЬ ГРАММАТИКУ";
    document.getElementById('id-grammar-input').value="";
    document.getElementById('title-grammar-input').value="";
    document.getElementById('content-grammar').value="";
    document.getElementById('example-grammar').value="";
})