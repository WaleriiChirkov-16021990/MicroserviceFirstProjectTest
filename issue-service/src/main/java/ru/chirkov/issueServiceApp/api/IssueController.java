package ru.chirkov.issueServiceApp.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chirkov.issueServiceApp.dto.IssueDto;
import ru.chirkov.issueServiceApp.model.Issue;
import ru.chirkov.issueServiceApp.service.IssueService;
import ru.chirkov.issueServiceApp.util.customValidator.IssueCustomValidator;
import ru.chirkov.issueServiceApp.util.customeException.BadRequestIssueCustomException;
import ru.chirkov.issueServiceApp.util.customeException.MyValidException;
import ru.chirkov.issueServiceApp.util.customeException.NotFoundIssueCustomException;
import ru.chirkov.issueServiceApp.util.response.AddErrorMessage;
import ru.chirkov.issueServiceApp.util.response.ResponseByException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/issues", consumes = "application/json")
public class IssueController {
    private final IssueService issueService;
    private final ModelMapper mapper;
    private final IssueCustomValidator customValidator;

    @GetMapping
    public ResponseEntity<List<IssueDto>> getIssues() {
        return ResponseEntity.ok(issueService.getIssues()
                .stream().map((element) -> mapper.map(element, IssueDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDto> getIssue(@PathVariable Long id) throws NotFoundIssueCustomException {
        return ResponseEntity.ok(mapper.map(issueService.getIssueById(id), IssueDto.class));
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<IssueDto>> getIssuesByReaderId(@PathVariable Long readerId) throws NotFoundIssueCustomException {
        return ResponseEntity.ok(issueService.getIssuesByReaderId(readerId)
                .stream().map((element) -> mapper.map(element, IssueDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<IssueDto>> getIssuesByBookId(@PathVariable Long bookId)
            throws NotFoundIssueCustomException {
        return ResponseEntity.ok(issueService.getIssuesByBookId(bookId)
                .stream()
                .map((element) -> mapper.map(element, IssueDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@RequestBody @Valid IssueDto issueDto, BindingResult bindingResult)
            throws MyValidException {
        customValidator.validate(issueDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MyValidException(AddErrorMessage.addErrorMessageFromBindingResult(bindingResult));
        }
        Issue issue = mapper.map(issueDto, Issue.class);
        return ResponseEntity.ok(mapper.map(issueService.createIssue(issue), IssueDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) throws BadRequestIssueCustomException {
        issueService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDto> updateIssue(@PathVariable Long id, @RequestBody @Valid IssueDto issueDto,
                                                BindingResult bindingResult) throws MyValidException {

        Issue issue = mapper.map(issueDto, Issue.class);
        customValidator.validate(issueDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MyValidException(AddErrorMessage.addErrorMessageFromBindingResult(bindingResult));
        }
        issue.setId(id);
        return ResponseEntity.ok(mapper.map(issueService.createIssue(issue), IssueDto.class));
    }

    @ExceptionHandler({MyValidException.class, BadRequestIssueCustomException.class})
    public ResponseEntity<ResponseByException> handleException(Exception e) {
        ResponseByException responseByException = new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName()
        );
        return new ResponseEntity<>(responseByException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundIssueCustomException.class)
    public ResponseEntity<ResponseByException> handleException(NotFoundIssueCustomException e) {
        ResponseByException responseByException = new ResponseByException(
                e.getMessage(),
                System.currentTimeMillis(),
                e.getClass().getSimpleName()
        );
        return new ResponseEntity<>(responseByException, HttpStatus.NOT_FOUND);
    }
}
