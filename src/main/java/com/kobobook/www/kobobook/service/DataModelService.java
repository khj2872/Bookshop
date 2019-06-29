package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.DataModel;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.repository.DataModelRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataModelService {

    @Autowired
    private DataModelRepository dataModelRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    public void createDataModel(Integer memberId, Item item) {
        Member member = memberRepository.findById(memberId).orElse(null);
        DataModel dataModel = dataModelRepository.findByMemberAndItem(member, item);
        if(dataModel == null) {
            dataModel = DataModel.createDataModel(member, item);
            dataModelRepository.save(dataModel);
        } else {
            dataModel.setCount(dataModel.getCount() + 1);
            dataModelRepository.save(dataModel);
        }

    }
}
