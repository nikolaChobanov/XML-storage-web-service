package docu.journal.service;

import docu.journal.model.Entry;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;

public interface EntryFileService {

    Entry saveEntry(Entry entry);

    void deleteAll();

    MappingJacksonValue findByPage(Pageable pageable, Boolean sensitiveData);

}
