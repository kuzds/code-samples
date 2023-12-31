package ru.kuzds.paging;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class PagedController {

    private final MovieCharacterRepository characterRepository;

    @GetMapping("/characters/page")
    Page<MovieCharacter> loadCharactersPage(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        return characterRepository.findAllPage(pageable);
    }

    @GetMapping("/characters/qualifier")
    Page<MovieCharacter> loadCharactersPageWithQualifier(@Qualifier("my") Pageable pageable) {
        return characterRepository.findAllPage(pageable);
    }

    @GetMapping("/characters/sorted")
    List<MovieCharacter> loadCharactersSorted(Sort sort) {
        return characterRepository.findAllSorted(sort);
    }

    @GetMapping("/characters/slice")
    Slice<MovieCharacter> loadCharactersSlice(Pageable pageable) {
        return characterRepository.findAllSlice(pageable);
    }
}
