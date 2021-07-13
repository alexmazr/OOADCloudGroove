function changeFormType ()
{
    let form = document.getElementById("userForm");
    let pvd = document.getElementById("password-verify-div");
    let submit = document.getElementById("userSubmit");
    let url = window.location.origin;

    // Change form to signup form
    if (form.action === url + "/login")
    {
        form.action = "/signup";
        pvd.classList.remove("d-none");
        pvd.classList.add("d-block");
        submit.innerHTML = 'Signup';
    }
    else
    {
        form.action = "/login";
        pvd.classList.remove("d-block");
        pvd.classList.add("d-none");
        submit.innerHTML = 'Login   ';
    }
}