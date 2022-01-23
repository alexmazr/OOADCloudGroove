<#include "head.ftlh">
<!--
    POD_NAME ${POD_NAME}
    SONGSERVICE_HOST ${SONGSERVICE_HOST}
    USERSERVICE_HOST ${USERSERVICE_HOST}
    UPLOADSERVICE_HOST ${UPLOADSERVICE_HOST}
    SONGSERVICE_PORT ${SONGSERVICE_PORT}
    USERSERVICE_PORT ${USERSERVICE_PORT}
    UPLOADSERVICE_PORT ${UPLOADSERVICE_PORT}
-->
<div class="d-flex justify-content-center">
    <div class="d-flex flex-column col-6 mt-4">
        <div class="text-center">
            <h1>Cloud Groove</h1>
        </div>
        <form action="/login" method="post" id="userForm">
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="Password">
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
    </div>
</div>

