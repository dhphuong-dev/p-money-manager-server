package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.request.WalletRequestDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class WalletController {

    private final WalletService walletService;

    @Tag(name = "wallet-controller")
    @Operation(summary = "API get wallet by id")
    @GetMapping(UrlConstant.Wallet.GET_WALLET_BY_ID)
    public ResponseEntity<?> getWalletsById(@Parameter(name = "user", hidden = true)
                                              @CurrentUser UserPrincipal user,
                                            @RequestParam String id) {
        return VsResponseUtil.success(walletService.getWalletResponseDtoById(id));
    }

    @Tag(name = "wallet-controller")
    @Operation(summary = "API create new wallet")
    @PostMapping(UrlConstant.Wallet.POST_NEW_WALLET)
    public ResponseEntity<?> createNewWallet(@Valid @RequestBody WalletRequestDto walletRequestDto,
                                             @Parameter(name = "user", hidden = true)
                                             @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(walletService.createNewWallet(user.getId(), walletRequestDto));
    }

    @Tag(name = "wallet-controller")
    @Operation(summary = "API update name wallet")
    @PostMapping(UrlConstant.Wallet.UPDATE_NAME_WALLET)
    public ResponseEntity<?> updateWalletName(@PathVariable String id, @RequestParam String name,
                                              @Parameter(name = "user", hidden = true)
                                              @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(walletService.updateWalletName(id, name, user.getId()));
    }

    @Tag(name = "wallet-controller")
    @Operation(summary = "API delete wallet")
    @DeleteMapping(UrlConstant.Wallet.DELETE_WALLETS)
    public ResponseEntity<?> deleteWallet(@PathVariable String id,
                                          @Parameter(name = "user", hidden = true)
                                          @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(walletService.deleteWallet(id, user.getId()));
    }

    @Tag(name = "wallet-controller")
    @Operation(summary = "API get wallets of current user")
    @GetMapping(UrlConstant.Wallet.GET_MY_WALLETS)
    public ResponseEntity<?> getWalletsByUser(@Parameter(name = "user", hidden = true)
                                              @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(walletService.getWalletsDtoByUser(user.getId()));
    }
}
