package com.thai27.chiatien.Service;

import com.thai27.chiatien.DTO.Data.UserListDto;
import com.thai27.chiatien.DTO.Request.SplitBillRequest;
import com.thai27.chiatien.DTO.Response.BillBankQrResponse;
import com.thai27.chiatien.DTO.Response.SplitBillResponse;
import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Ulti.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    public ResponseModel<SplitBillResponse> splitBill(SplitBillRequest splitBillRequest) {
        Map<Long, Long> paidAmount = splitBillRequest.getPaidAmount();
        if (paidAmount == null) paidAmount = Collections.emptyMap();
        List<Long> userIds = new ArrayList<>(paidAmount.keySet());
        List<ChiaTienUser> listUserDto = chiaTienUserRepository.findUserByListId(userIds);
        Map<Long, String> idToUsername = listUserDto.stream()
                .collect(Collectors.toMap(ChiaTienUser::getId, ChiaTienUser::getUsername));

        int memberCount = idToUsername.size();
        if (memberCount == 0) {
            SplitBillResponse emptyResp = new SplitBillResponse();
            emptyResp.setGetPaid(Collections.emptyMap());
            emptyResp.setMustPaid(Collections.emptyMap());
            return ResponseModel.warning(emptyResp, "Không tìm thấy user");
        }

        long totalAmount = paidAmount.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        long averageAmount = totalAmount / memberCount;

        Map<String, Long> mustPaid = new HashMap<>();
        Map<String, Long> getPaid = new HashMap<>();

        for (Map.Entry<Long, String> entry : idToUsername.entrySet()) {
            Long id = entry.getKey();
            String username = entry.getValue();

            Long paid = paidAmount.getOrDefault(id, 0L);

            if (paid > averageAmount) {
                getPaid.put(username, paid - averageAmount);
            } else if (paid < averageAmount) {
                mustPaid.put(username, averageAmount - paid);
            }
        }

        SplitBillResponse splitBillResponse = new SplitBillResponse();
        splitBillResponse.setGetPaid(getPaid);
        splitBillResponse.setMustPaid(mustPaid);
        splitBillResponse.setAverageAmount(averageAmount);
        splitBillResponse.setTotalAmount(totalAmount);
        return ResponseModel.success(splitBillResponse, "Chia tiền thành công");
    }


}
