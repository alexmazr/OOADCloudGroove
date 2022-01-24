<#include "head.ftlh">
<div class="d-flex justify-content-center">
    <div class="d-flex flex-column col-8 mt-4">
        <div class="mb-4 d-flex justify-content-center">
            <#if songLink??>
            <div class="d-flex flex-column text-center">
                <audio controls autoplay>
                    <source src="${songLink}" type="audio/mp3">
                </audio>
                <h6>Currently Playing: ${songTitle}</h6>
            </div>
            <#else>
                <div class="d-flex flex-column text-center">
                    <audio controls></audio>
                    <h6>No Song Playing</h6>
                </div>
            </#if>
        </div>
        <form action="/user/upload" method="post" id="testUpload" enctype="multipart/form-data">
            <h1>Upload A New Song</h1>
            <input type="hidden" name="userID" value="${userID}">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" name="title" class="form-control" id="title" aria-describedby="titleHelp" placeholder="Enter Song Title">
            </div>
            <div class="form-group">
                <label for="artist">Artist</label>
                <input type="artist" name="artist" class="form-control" id="artist" aria-describedby="artistHelp" placeholder="Enter Song Artist">
            </div>
            <br>
            <input type="file" id="newUpload" name="newUpload">
            <button id="testSubmit" type="submit" class="btn btn-primary">upload</button>
        </form>
        <div class="card mb-4">
            <div class="card-header">
                My Media
            </div>
            <ul class="list-group list-group-flush">
                <#if playlists??>
                    <#list playlists as playlist>
                        <li class="list-group-item">Playlist: <a href="/playlist/${playlist.playlistId}/${playlist.name}">${playlist.name}</a></li>
                    </#list>
                <#else>
                    <li class="list-group-item">You don't have any playlists :(</li>
                </#if>
                <#if songs??>
                    <#list songs as song>
                        <li class="list-group-item">Song: <a href="/user/${userID}/song/${song.title}">${song.title}</a></li>
                    </#list>
                <#else>
                    <li class="list-group-item">You don't have any songs :(</li>
                </#if>
            </ul>
        </div>
    </div>
</div>