<#include "head.ftlh">
<div class="d-flex justify-content-center">
    <div class="d-flex flex-column col-8 mt-4">
        <div class="card">
            <div class="card-header">
                <#if user??>${user.name}'s</#if> Playlists
            </div>
            <ul class="list-group list-group-flush">
                <#if user??>
                    <#list user.playlists as playlist>
                        <li class="list-group-item"><a href="/playlist/${playlist.playlistId}/${playlist.name}">${playlist.name}</a></li>
                    </#list>
                <#else>
                    <li class="list-group-item">You don't have any songs :(</li>
                </#if>

            </ul>
        </div>
    </div>
</div>