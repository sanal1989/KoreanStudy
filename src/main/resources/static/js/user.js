const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function renderDataUserTable(data) {
    let tableHeader = "<table id='user-table' className='table mt-3'><thead><tr><th>№</th><th>mail</th><th>delete</th><th>edit</th></tr></thead><tbody>";
    let tableContent = "";
    for(let i = 0; i < data.length; i++) {
        tableContent = tableContent + "<tr>" +
            "<td id=\"tr_id_user_"+ data[i].id+"\">"+ data[i].id+"</td>" +
            "<td id=\"tr_name_user_" + data[i].id + "\"><a href=\"/useres/" + data[i].id + "\"><button class=\"btn btn-primary\">"+data[i].mail+"</button></a></td>" +
            "<td id=\"tr_delete_user_" + data[i].id + "\"><button class='btn btn-primary' onclick=\"deleteUser(" + data[i].id + ")\">delete</button></td>" +
            "<td id=\"tr_edit_user_" + data[i].id + "\"><button class='btn btn-primary' data-bs-toggle=\"modal\" data-bs-target=\"#userModal\" onclick=\"editUser(" + data[i].id + ")\">edit</button></td>";

    }
    let tableFooter = "</tbody></table>";
    let currentNode = document.getElementById('user-table');
    let newNode = document.createElement('table');
    newNode.classList.add("table");
    newNode.classList.add("mt-3");
    newNode.setAttribute("id","user-table");
    newNode.innerHTML = tableHeader + tableContent + tableFooter;
    setTimeout(()=>{
        currentNode.parentNode.replaceChild(newNode, currentNode);
    },1000);
}


function submitUserForm() {
    let result = [];
    let options = document.getElementById('role-user');
    let opt;
    for(let i=0; i<options.length; i++) {
        opt = options[i];
        if (opt.selected) {
            result.push(opt.value);
        }
    }
    const formData = {
        id:document.getElementById('id-user-input').value,
        name: document.getElementById('name-user-input').value,
        mail: document.getElementById('mail-user-input').value,
        password: document.getElementById('password-user-input').value,
        csrfHeader:csrfToken,
        roles: result
    };
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    let response = fetch("/user", {

        method: "POST",
        body: JSON.stringify(formData),
        headers: header,
    }).then(response => response.json())
        .then(data =>{
            document.getElementById('add-new-user-button').click();
            renderDataUserTable(data)
        })
        .catch(error => console.error('Error:', error));
}

function editUser(user_id){
    document.getElementById('modal-user').innerHTML = "EDIT USER";
    let response = fetch("/user/"  + user_id, {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            csrfHeader:csrfToken,
        }
    }).then(response => response.json())
        .then(data =>{
            document.getElementById('id-user-input').value = data.id;
            document.getElementById('name-user-input').value = data.name;
            document.getElementById('mail-user-input').value = data.mail;
            document.getElementById('password-user-input').value = data.password;
            let selectElement = document.getElementById('role-user');
            for(let i=0; i<data.roles.length; i++){
                if(data.roles[i]==='ADMIN')selectElement[0].selected=true;
                if(data.roles[i]==='USER')selectElement[1].selected=true;
                }

            }
        )
        .catch(error => console.error('Error:', error));
}
function deleteUser(user_id){
    let header = new Headers();
    header.append("Content-Type","application/json;charset=UTF-8");
    header.append(csrfHeader,csrfToken);
    console.log(header);
    let response = fetch("/user/" + user_id, {
        method: "DELETE",
        headers: header
    }).then(response => response.json())
        .then(data => renderDataUserTable(data))
        .catch(error => console.error('Error:', error));
}

const UserModal = document.getElementById('userModal')
UserModal.addEventListener('hide.bs.modal', event => {

    document.getElementById('modal-user').innerHTML = "ADD USER";
    document.getElementById('id-user-input').value = "";
    document.getElementById('name-user-input').value = "";
    document.getElementById('mail-user-input').value = "";
    document.getElementById('password-user-input').value = "";
    let options = document.getElementById('role-user') ;
    let admin = new Option('ADMIN','ADMIN');
    let user = new Option('USER','USER');
    options.remove(1);
    options.remove(0);
    options.add(admin);
    options.add(user);
})