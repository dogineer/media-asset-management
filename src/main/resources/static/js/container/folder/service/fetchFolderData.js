import {ClipController} from "../../clip/controller/ClipController.js";
import {handleException, serverError} from "../../issue/service/IssueService.js";

const clearClipTrField = () => {
    const elements = document.getElementById('clipListBody')

    if (elements !== null) {
        elements.replaceChildren()
    }

    return elements
}

const generateThumbnailUrl = (ingestAt, clipUuid) => {
    const currentProtocol = window.location.protocol;
    const filename = ingestAt + '/' + clipUuid + '.jpg';
    return currentProtocol + '/clip/thumbnail?filename=' + filename;
}

const createClipData = (parentElement, item, index) => {
    const tr = document.createElement('tr')
    parentElement.appendChild(tr)

    tr.id = 'clip'
    tr.className = 'clip-edit'
    tr.setAttribute('data-clip-id', item.clip_id)
    tr.setAttribute('data-clip-title', item.file_name)
    tr.setAttribute('data-clip-data', item.ingest_at)
    tr.setAttribute('data-clip-uuid', item.clip_uuid)
    tr.setAttribute('data-clip-path', item.file_path)

    const count = document.createElement('td')
    const preview = document.createElement('td')
    const teamId = document.createElement('td')
    const teamName = document.createElement('td')
    const folderName = document.createElement('td')
    const fileUUID = document.createElement('td')
    const fileName = document.createElement('td')
    const filePath = document.createElement('td')
    const fileFormat = document.createElement('td')
    const fileSize = document.createElement('td')

    const previewAtag = document.createElement('a')
    const previewThumbnail = document.createElement('img')

    tr.appendChild(count)
    tr.appendChild(preview)
    tr.appendChild(teamId)
    tr.appendChild(teamName)
    tr.appendChild(folderName)
    tr.appendChild(fileUUID)
    tr.appendChild(fileName)
    tr.appendChild(filePath)
    tr.appendChild(fileFormat)
    tr.appendChild(fileSize)

    count.className = 'clip-count'
    count.innerText = index + 1

    preview.className = 'clip-preview'
    preview.appendChild(previewAtag)
    previewAtag.setAttribute("type", "button")
    previewAtag.setAttribute("id", "clip-preview-btn")
    previewAtag.addEventListener('click', (e) => {
        const trElement = e.target.closest('tr'); // tr 요소 찾기
        if (trElement) {
            const clipId = trElement.getAttribute('data-clip-id');
            const clipPath = trElement.getAttribute('data-clip-path');

            console.log('[+] 클립 프리뷰 버튼 클릭');
            console.log('클립 ID: ' + clipId);
            console.log('클립 PATH: ' + clipPath);

            ClipController.showPreview(e);
        }
        e.stopPropagation();
    });

    previewAtag.appendChild(previewThumbnail)
    previewThumbnail.setAttribute("class", "thumbnail")
    previewThumbnail.setAttribute("src", generateThumbnailUrl(item.ingest_at, item.clip_uuid))
    previewThumbnail.setAttribute("alt", "thumbnail")

    teamId.className = 'clip-team_id'
    teamId.innerText = 'T' + item.team_id

    teamName.className = 'clip-team_name'
    teamName.innerText = item.team_name

    folderName.className = 'clip-folder_name'
    folderName.innerText = item.folder_name

    fileUUID.className = "clip-file-uuid"
    fileUUID.innerText = item.clip_uuid

    fileName.className = 'clip-file_name'
    fileName.innerText = item.file_name

    const spanFilePath = document.createElement('span')

    filePath.appendChild(spanFilePath)
    spanFilePath.className = 'clip-file_path'
    spanFilePath.innerText = item.file_path

    fileFormat.className = 'clip-file_format'
    fileFormat.innerText = item.format_long_name

    fileSize.className = 'clip-file_size'
    fileSize.innerText = item.size + " MB"

    tr.addEventListener('click', (e) => {
        console.log("[+] 클립 선택")
        console.log("클립 ID: " + tr.getAttribute("data-clip-id"))
        console.log("클립 PATH: " + tr.getAttribute("data-clip-path"))

        ClipController.importPremierPro(tr);
    })
}

export const fetchFolderDataForClipView = (folderId) => {
    fetch('/folder/select/' + folderId, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'},
    })
        .then(res => {
            if (res.ok) {
                return res.json()
            } else {
                res.json().then(errorData => {
                    handleException(errorData)
                });
            }
        })
        .then(item => {
            const parentElement = clearClipTrField()

            item.forEach((item, index) => {
                createClipData(parentElement, item, index)
            })
        })
        .catch(error => {
            serverError(error);
        });
}