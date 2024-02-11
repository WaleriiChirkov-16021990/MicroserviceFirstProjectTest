package ru.chirkov.issueServiceApp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chirkov.issueServiceApp.dao.IssueRepository;
import ru.chirkov.issueServiceApp.dto.IssueDto;
import ru.chirkov.issueServiceApp.model.Issue;
import ru.chirkov.issueServiceApp.util.customeException.BadRequestIssueCustomException;
import ru.chirkov.issueServiceApp.util.customeException.NotFoundIssueCustomException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;


    public void deleteById(Long id) {
        try {
            issueRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestIssueCustomException("Can't delete issue with id " + id, e);
        }
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).orElseThrow(() ->
                new NotFoundIssueCustomException("Issue with id " + id + " not found"));
    }


    public List<Issue> getIssues() {
        try {
            return issueRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestIssueCustomException("Can't get issues", e);
        }
    }

    public Issue createIssue(Issue issue) {
        try{
            return issueRepository.save(issue);
        } catch (Exception e) {
            throw new BadRequestIssueCustomException("Can't create issue", e);
        }
    }

    public List<Issue> getIssuesByReaderId(Long readerId) {
        return issueRepository.getIssuesByReaderId(readerId).orElseThrow(() ->
                new NotFoundIssueCustomException("Issues with readerId " + readerId + " not found"));
    }

    public List<Issue> getIssuesByBookId(Long bookId) {
        return issueRepository.getIssuesByBookId(bookId).orElseThrow(() ->
                new NotFoundIssueCustomException("Issues with bookId " + bookId + " not found"));
    }
}
