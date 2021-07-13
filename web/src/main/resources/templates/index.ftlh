<#include "head.ftlh">
<div class="d-flex justify-content-center">
    <div class="d-flex flex-column col-6 mt-4">
        <form action="/login" method="post" id="userForm">
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" placeholder="Password">
            </div>
            <div class="form-group d-none" id="password-verify-div">
                <label for="password-verify">Verify Password</label>
                <input type="password" class="form-control" id="password-verify" placeholder="Verify Password">
            </div>
            <div class="form-check form-switch">
                <input type="checkbox" class="form-check-input" id="userSwitch" oninput="changeFormType()">
                <label class="form-check-label" for="userSwitch">Login / Signup</label>
            </div>

            <button id="userSubmit" type="submit" class="btn btn-primary">Login</button>
        </form>

        <form action="/user/1/playlist/1/dog/upload" method="post" id="testUpload" enctype="multipart/form-data">
            <input type="file" id="newUpload" name="newUpload">
            <button id="testSubmit" type="submit" class="btn btn-primary">upload</button>
        </form>
    </div>
</div>

