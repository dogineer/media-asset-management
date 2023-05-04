package com.develop.web.domain.folder.controller;

import com.develop.web.domain.folder.dto.FolderDto;
import com.develop.web.domain.folder.service.ChildrenFolderFetcher;
import com.develop.web.domain.folder.service.CreateNewFolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "프로그램 폴더 관리", description = "폴더 생성 및 조회")
@RequestMapping(value = "/folder")
public class FolderController {
    private final CreateNewFolder createNewFolder;
    private final ChildrenFolderFetcher childrenFolderFetcher;

    @PostMapping("/create")
    @Operation(summary = "폴더 생성", description = "폴더를 생성합니다.")
    public void createNewFolder(@RequestBody FolderDto folderDto){
        createNewFolder.addFolder(folderDto);
    }

    @GetMapping("/show/all")
    @Operation(summary = "폴더 전체 조회", description = "폴더 전체를 조회합니다.")
    public void ShowAllFolder(@RequestBody FolderDto folderDto){
        createNewFolder.addFolder(folderDto);
    }

    @GetMapping("/show/{num}")
    @Operation(summary = "하위 폴더 조회", description = "상위 폴더 안에 있는 하위 폴더를 조회합니다. 상위 폴더의 코드를 입력해주세요.")
    public List<FolderDto> ShowFolderList(@PathVariable Integer num){

        return childrenFolderFetcher.getChildrenFolder(num);
    }
}