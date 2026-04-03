package com.auth.user.service.helper;

import com.auth.user.model.dao.RefreshTokenUserMappingDao;
import com.auth.user.repository.RefreshTokenUserMappingRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RefreshTokenUserMappingHelperService {

    private final RefreshTokenUserMappingRepository refreshTokenUserMappingRepository;

    public RefreshTokenUserMappingHelperService(RefreshTokenUserMappingRepository refreshTokenUserMappingRepository){
        this.refreshTokenUserMappingRepository = refreshTokenUserMappingRepository;
    }

    public void saveRefreshToken(RefreshTokenUserMappingDao refreshTokenUserMappingDao){
        RefreshTokenUserMappingDao existingToken = refreshTokenUserMappingRepository.findByUsername(refreshTokenUserMappingDao.getUsername()).orElse(null);

        if(Objects.nonNull(existingToken)){
            refreshTokenUserMappingRepository.deleteById(existingToken.getId());
        }

        refreshTokenUserMappingRepository.save(refreshTokenUserMappingDao);
    }

    public void deleteRefreshToken(String token){
        refreshTokenUserMappingRepository.deleteByToken(token);
    }

    public RefreshTokenUserMappingDao getRefreshTokenMappingByToken(String token){
        return refreshTokenUserMappingRepository.findByToken(token).orElse(null);
    }
}
