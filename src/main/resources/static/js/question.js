function renderDataQuestionTable(data) {
    let tableHeader = "<table id='topik-question-table' className='table mt-3 text-center'><thead><tr><th>№</th><th>тип вопроса</th><th sec:authorize=\"hasRole('ADMIN')\">удалить</th><th sec:authorize=\"hasRole('ADMIN')\">редактировать</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        let typeQuestion = "<td id=\"tr_type_question_" + data[i].id + "\"><p>듣기</p></td>";
        console.log(data[i].type);
        if(data[i].type === 'READING') typeQuestion = "<td id=\"tr_type_question_" + data[i].id + "\"><p>읽기</p></td>";
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_number_question_" + data[i].id + "\"><button class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#questionOpenModal\" onclick=\"openQuestion(" +data[i].id +")\">"+data[i].number+"</button></td>" +
            typeQuestion +
            "<td "+'sec:authorize=\"hasRole(\'ADMIN\')\"' +"id =\"tr_delete_question_"  + data[i].id + "\">" +
            "<button class='btn btn-primary' onclick=\"deleteQuestion("  + data[i].id + ")\">удалить</button></td>"+
            "<td "+'sec:authorize=\"hasRole(\'ADMIN\')\"'+ "id = \"tr_edit_question_"  + data[i].id + "\">"+
            "<button class='btn btn-primary' data-bs-toggle='modal' data-bs-target='#questionModal' onclick=\"editQuestion(" + data[i].id +")\">редактировать</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('topik-question-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","topik-question-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },1000);
}

function submitQuestionForm(topikId){

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    let typeQuestion;
    let options = document.getElementById('question-type');
    let opt;
    for(let i=0; i<options.length; i++) {
        opt = options[i];
        if (opt.selected) {
            typeQuestion = opt.value;
        }
    }

    const formData = {
        id:document.getElementById('id-question-input').value,
        number: document.getElementById('number-question-input').value,
        type: typeQuestion,
        question: document.getElementById('question-text').value,
        answer:document.getElementById('answer-question-input').value,
        words:document.getElementById('question-word').value,
        grammars:document.getElementById('question-grammar').value,
    };
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/question/" + topikId, {
        method: "POST",
        body: JSON.stringify(formData),
        headers: header,
    }).then(response => response.json())
        .then(data =>{
            document.getElementById('add-new-question-button').click();
            renderDataQuestionTable(data)
        })
        .catch(error => console.error('Error:', error));
}

function editQuestion(question_id){
    document.getElementById('modal-question').innerHTML = "РЕДАКТИРОВАТЬ ВОПРОС";

    let response = fetch("/question/"  + question_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
                document.getElementById('id-question-input').value = data.id;
                document.getElementById('number-question-input').value = data.number;
                document.getElementById('question-text').value = data.question;
                document.getElementById('answer-question-input').value = data.answer;
                document.getElementById('question-word').value = data.words;
                document.getElementById('question-grammar').value = data.grammars;
                let selectElement = document.getElementById('question-type');
                if(data.type === 'LISTENING')selectElement[1].selected=true;
                if(data.type ==='READING')selectElement[0].selected=true;
            }
        )
        .catch(error => console.error('Error:', error));
}

function deleteQuestion(question_id){
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/question/" + question_id, {
        method: "DELETE",
        headers: header
    }).then(response => response.json())
        .then(data => renderDataQuestionTable(data))
        .catch(error => console.error('Error:', error));
}

const questionModal = document.getElementById('questionModal')
questionModal.addEventListener('hide.bs.modal', event => {

    document.getElementById('modal-question').innerHTML = "ДОБАВИТЬ ВОПРОС";
    document.getElementById('id-question-input').value = "";
    document.getElementById('number-question-input').value = "";
    document.getElementById('question-text').value = "";
    document.getElementById('answer-question-input').value = "";
    document.getElementById('question-word').value = "";
    document.getElementById('question-grammar').value = "";
    let options = document.getElementById('question-type') ;
    let reading = new Option('읽기','READING');
    let listening = new Option('듣기','LISTENING');
    options.remove(1);
    options.remove(0);
    options.add(reading);
    options.add(listening);
});

function openQuestion(question_id){
    let response = fetch("/question/"  + question_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then(response => response.json())
        .then(data =>{
                console.log(data);
                document.getElementById('question-open-number').innerHTML = data.number;
                if(data.type == 'READING'){
                    document.getElementById('question-open-type').innerHTML = '읽기';
                }else{
                    document.getElementById('question-open-type').innerHTML = '듣기';
                }

                document.getElementById('question-open-question').innerHTML = data.question.replace(/(?:\r\n|\r|\n)/g, '<br />');
                document.getElementById('question-open-answer').innerHTML = data.answer;
                document.getElementById('question-open-worlds').innerHTML = data.words.replace(/(?:\r\n|\r|\n)/g, '<br />');
                document.getElementById('question-open-grammars').innerHTML = data.grammars.replace(/(?:\r\n|\r|\n)/g, '<br />');

            }
        )
        .catch(error => console.error('Error:', error));
}