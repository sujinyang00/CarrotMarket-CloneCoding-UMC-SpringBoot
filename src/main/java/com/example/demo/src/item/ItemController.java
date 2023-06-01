package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.item.model.PatchUserReq;
import com.example.demo.src.item.model.User;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.item.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    private final ItemProvider itemProvider;

    @Autowired
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    private final JwtService jwtService;




    public ItemController(UserProvider userProvider, ItemProvider itemProvider, UserService userService, ItemService itemService, JwtService jwtService){
        this.userProvider = userProvider;
        this.itemProvider = itemProvider;
        this.userService = userService;
        this.itemService = itemService;
        this.jwtService = jwtService;
    }

    /**
     * 중고거래 물품 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/item
    public BaseResponse<List<GetItemRes>> getItems(@RequestParam(required = false) Integer Category) {
        try{
            if(Category == null){
                List<GetItemRes> getItemRes = itemProvider.getItems();
                return new BaseResponse<>(getItemRes);
            }
            // Get Users
            List<GetItemRes> getItemRes = itemProvider.getItemsByCategory(Category);
            return new BaseResponse<>(getItemRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 아이템 1개 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{itemId}") // (GET) 127.0.0.1:9000/item/:itemId
    public BaseResponse<GetItemRes> getItem(@PathVariable("itemId") int itemId) {
        // Get Users
        try{
            GetItemRes getItemRes = itemProvider.getItem(itemId);
            return new BaseResponse<>(getItemRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 아이템 등록 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/postItem") // (POST) 127.0.0.1:9000/item/postItem
    public BaseResponse<PostItemRes> postItem(@RequestBody PostItemReq postItemReq) {
        if(postItemReq.getPostTitle() == null){
            return new BaseResponse<>(POST_ITEM_EMPTY_STRING);
        }

        try{
            PostItemRes postItemRes = itemService.createItem(postItemReq);
            return new BaseResponse<>(postItemRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 아이템 삭제 API
     * [DELETE] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @DeleteMapping("/deleteItem") // (DELETE) 127.0.0.1:9000/item/deleteItem
    public BaseResponse<DeleteItemRes> deleteItem(@RequestBody DeleteItemReq deleteItemReq) {
        if(deleteItemReq == null){
            return new BaseResponse<>(POST_ITEM_EMPTY_STRING);
        }
        try{
            DeleteItemRes deleteItemRes = itemService.deleteItem(deleteItemReq);
            return new BaseResponse<>(deleteItemRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 사용자 이름 변경
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/changeName/{userIdx}") // (PATCH) 127.0.0.1:9000/item/changeName/:userIdx
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
            itemService.modifyUserNick(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 중고거래 게시글 수정
     * [PUT]
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PutMapping("/modifyItem/{userIdx}") // (PUT) 127.0.0.1:9000/item/modifyItem/:userIdx
    public BaseResponse<String> modifyItemPost(@PathVariable("itemId") int itemId, @PathVariable("postWriterId") int postWriterId, @RequestBody Item item){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(postWriterId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 게시글 내용 변경
            // itemId에 해당하는 게시글을 데이터베이스에서 가져오기
            GetItemRes getItemRes = itemProvider.getItem(itemId);
            if (getItemRes == null) {
                // itemId에 해당하는 게시글이 없는 경우 예외 처리
                //return ResponseEntity.notFound().build();
                String result = "";
                return new BaseResponse<>(result);
            }

            PutItemPostReq putItemPostReq = new PutItemPostReq(itemId, item.getItemCategory(), item.getPostWrittenDate(), item.getPostTitle(), item.getPostContent(), item.getPostImgUrl(), item.getItemPrice(), item.getItemStatus(), item.getLocationCode(), item.getPriceOffer());

            // 게시글 업데이트
            itemService.modifyItemPost(putItemPostReq);

            //return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
            String result = "게시글이 성공적으로 수정되었습니다.";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }





}