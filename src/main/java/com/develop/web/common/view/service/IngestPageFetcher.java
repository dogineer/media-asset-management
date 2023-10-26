package com.develop.web.common.view.service;

import com.develop.web.common.view.dto.AccountDto;
import com.develop.web.domain.admin.user.service.UserManagementService;
import com.develop.web.domain.service.folder.service.ProgramFolderListFetcherService;
import com.develop.web.domain.service.folder.service.ProgramFolderFetcherService;
import com.develop.web.domain.service.ingest.service.IngestListFetcherService;
import com.develop.web.common.view.dto.CriteriaDto;
import com.develop.web.common.view.dto.PageDto;
import com.develop.web.domain.service.ingest.mapper.UploadMapper;
import com.develop.web.domain.admin.notice.service.PostListFetcher;
import com.develop.web.domain.member.program.service.ProgramListFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Component("ingestPageFetcher")
public class IngestPageFetcher implements PageingService{
    private final PostListFetcher postListFetcher;
    private final UserManagementService userManagementService;
    private final ProgramListFetcher programListFetcher;
    private final ProgramFolderFetcherService programFolderFetcherService;
    private final IngestListFetcherService ingestListFetcherService;
    private final ProgramFolderListFetcherService programFolderListFetcherService;

    private final UploadMapper uploadMapper;

    @Override
    public void fetchPageing(CriteriaDto criteriaDto, AccountDto accountDto, Model model) {
        String account = accountDto.getAccount();
        Integer programId = accountDto.getProgramId();
        Boolean isAdmin = accountDto.getIsAdmin();

        int countTotal = uploadMapper.selectIngestCount();
        PageDto pageDto = new PageDto(countTotal, 10, criteriaDto);

        model.addAttribute("NoticeList", postListFetcher.getPost());
        model.addAttribute("CurrentProgram", programListFetcher.findCurrentProgram(programId, isAdmin));
        model.addAttribute("MemberInfo", userManagementService.findMember(account));
        model.addAttribute("ProgramFolderAllList", programFolderListFetcherService.findProgramFolder(programId, isAdmin));
        model.addAttribute("ProgramFolderRootList", programFolderFetcherService.findProgramFolderRoot(programId, isAdmin));
        model.addAttribute("IngestRequestList", ingestListFetcherService.findIngestRequestList(criteriaDto));

        model.addAttribute("pageMaker", pageDto);

    }
}
