package docu.journal.service;

import docu.journal.exception.JournalNotFoundException;
import docu.journal.model.Entry;
import docu.journal.model.Views;
import docu.journal.repository.EntryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@Service
public class EntryFileServiceImpl implements EntryFileService {

    @Autowired
    private EntryRepository repo;


    @Override
    public Entry saveEntry(Entry entry) {
        repo.save(entry);
        return entry;
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public MappingJacksonValue findByPage(Pageable pageable, Boolean sensitiveData) {
        Page<Entry> dataPages = repo.findAll(pageable);

        CompletableFuture<Page<Entry>> futureData = CompletableFuture.supplyAsync(() -> {

            if (!(dataPages.hasContent())) {
                throw new RuntimeException();
            }
            return dataPages;
        });


        MappingJacksonValue jsonData;
        List<Entry> listData;
        try {
            // List<Entry> listData = futureData.get().getContent();
            listData = futureData.get().getContent();
            jsonData = new MappingJacksonValue(listData);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Could not display data as page " + pageable.getPageNumber() + " does not exist", e.getStackTrace());
            throw new JournalNotFoundException("Could not display data as page " + pageable.getPageNumber() + " does not exist");
        }

        //This if block distinguishes the returned data to either public values or private ones.
        if (sensitiveData) {
            jsonData.setSerializationView(Views.PublicData.class);
        } else {
            jsonData.setSerializationView(Views.Internal.class);
        }

        return jsonData;
    }


}
