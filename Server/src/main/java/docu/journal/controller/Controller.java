package docu.journal.controller;

import docu.journal.exception.JournalNotFoundException;
import docu.journal.model.Entry;
import docu.journal.service.ClientLimitManager;
import docu.journal.service.EntryFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
@Log4j2
public class Controller {

    //Database link
    @Autowired
    private EntryFileService service;

    @GetMapping(value = "/entry", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<MappingJacksonValue> getDataByPage
            (@RequestParam("page") Optional<Integer> page
                    , @RequestParam("pageSize") Optional<Integer> pageSize
                    , @RequestParam("public") Optional<String> sensitizePublic) {

        Integer startPage = page.orElse(1);
        Integer pageLength = pageSize.orElse(5);
        Boolean sensitiveData = Boolean.parseBoolean(sensitizePublic.orElse("true"));

        //Value used to make sure semaphore doesn't release when it has not been acquired
        Boolean failAcquire = true;
        try {
            failAcquire = ClientLimitManager.acquireAvailableSpace();

            if (startPage == 0) {
                log.error("Could not display data at page " + page.get() + " does as pages start from 1");
                throw new JournalNotFoundException("Could not display data as page " + page.get() + " does not exist");
            }

            //The request made to the database with startPage and pageLength which is how many journal entries to retrieve
            Pageable request = PageRequest.of(startPage - 1, pageLength);
            MappingJacksonValue jsonData = service.findByPage(request, sensitiveData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(jsonData, headers, HttpStatus.OK);
        } finally {
            if (!failAcquire) {
                ClientLimitManager.releaseAcquiredSpace();
            }
        }

    }


    @PostMapping(value = "/entry", consumes = "application/json", produces = "application/json")
    ResponseEntity addJournal(@RequestBody Entry entry) {

        //Value used to make sure semaphore doesn't release when it has not been acquired
        Boolean failAcquire = true;
        try {
            failAcquire = ClientLimitManager.acquireAvailableSpace();

            service.saveEntry(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body("HTTP/1.1" + " " + HttpStatus.CREATED);

        } finally {
            if (!failAcquire) {
                ClientLimitManager.releaseAcquiredSpace();
            }
        }
    }

  /*  //Simple method to clear up the database
    @DeleteMapping(value = "/all")
    public void del() {
        service.deleteAll();
    }

*/
}



