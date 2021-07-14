<#include "head.ftlh">
<div class="d-flex justify-content-center">
    <div class="d-flex flex-column col-8 mt-4">
        <div class="card">
            <div class="card-header">
                My Media
            </div>
            <ul class="list-group list-group-flush">
                <#if playlists??>
                    <#list playlists as playlist>
                        <li class="list-group-item"><a href="/playlist/${playlist.playlistId}/${playlist.name}">Playlist: ${playlist.name}</a></li>
                    </#list>
                <#else>
                    <li class="list-group-item">You don't have any playlists :(</li>
                </#if>
                <#if songs??>
                    <#list songs as song>
                        <li class="list-group-item"><a href="/user/${userID}/song/${song.filepath}">Song: ${song.title}</a></li>
                    </#list>
                <#else>
                    <li class="list-group-item">You don't have any songs :(</li>
                </#if>
            </ul>
        </div>
        <div>
            <#if songLink??>
                <audio controls>
                    <source src="${songLink}" type="audio/mp3">
                </audio>
            </#if>
        </div>
        <form action="/user/upload" method="post" id="testUpload" enctype="multipart/form-data">
            <input type="hidden" name="userID" value="${userID}">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" name="title" class="form-control" id="title" aria-describedby="titleHelp" placeholder="Enter Song Title">
            </div>
            <div class="form-group">
                <label for="artist">Artist</label>
                <input type="artist" name="artist" class="form-control" id="artist" aria-describedby="artistHelp" placeholder="Enter Song Artist">
            </div>
            <input type="file" id="newUpload" name="newUpload">
            <button id="testSubmit" type="submit" class="btn btn-primary">upload</button>
        </form>
    </div>
</div>