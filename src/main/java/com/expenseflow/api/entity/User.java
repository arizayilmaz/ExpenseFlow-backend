package com.expenseflow.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // DÜZELTME: UserDetails arayüzü için gerekli olan alanlar eklendi.
    // @Builder.Default, bu alanlara builder'da bir değer verilmezse varsayılan olarak 'true' atar.
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;


    // --- UserDetails Metotları ---
    // Bu metotlar artık yukarıdaki alanların değerlerini döndürür.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Rol bazlı yetkilendirme için, şimdilik null.
        return null;
    }

    @Override
    public String getUsername() {
        // Kullanıcı adı olarak e-posta adresini kullanıyoruz.
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}