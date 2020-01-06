function pageLoad() {

    document.getElementById("newuserdiv").style.display = 'none';

    if(window.location.search === '?logout') {
        document.getElementById('content').innerHTML = '<h1>Logging out, please wait...</h1>';
        logout();
    } else {
        document.getElementById("loginButton").addEventListener("click", login);
        document.getElementById("newUserButton").addEventListener("click", newuser);
        document.getElementById("createNewButton").addEventListener("click", newUserButton);
    }
}

function newUserButton(){
     document.getElementById("content").style.display = 'none';
     document.getElementById("newuserdiv").style.display = 'block';
 }

function login(event) {

    event.preventDefault();

    const form = document.getElementById("loginForm");
    const formData = new FormData(form);

    fetch("/Users/login", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.set("UserName", responseData.UserName);
            Cookies.set("UserToken", responseData.UserToken);

            window.location.href = '/client/index.html';
        }
    });
}
function newuser(event){

    // alert("Function Called");
    event.preventDefault();

    if(document.getElementById("NewUserName").value.trim() === ""){
             alert("Please Provide A Sensible Username");
        return;
    }
        if(document.getElementById("NewUserPassword").value.trim() === ""){
            alert("Please Provide A Password");
        return;
    }

   // alert("Step 2 Ok");

    const form = document.getElementById("newuserform");
    const formData = new FormData(form);

    fetch("/Users/new", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
          //  alert(responseData.error);
            alert("Username is not Unique Please Try Another.");
        } else {
            alert("User Created.");
        }
    });
}


function logout() {

    fetch("/Users/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            Cookies.remove("UserName");
            Cookies.remove("UserToken");


        }
    });
}
