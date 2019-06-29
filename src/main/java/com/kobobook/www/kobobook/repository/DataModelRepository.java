package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.DataModel;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataModelRepository extends JpaRepository<DataModel, Integer> {
    DataModel findByMemberAndItem(Member member, Item item);
}
