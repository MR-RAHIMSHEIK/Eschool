package com.jts.login;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;

public class JwtSecretMakerTest {

	@Test
	public void generateToken() {
		SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
		String secretKey =DatatypeConverter.printHexBinary(key.getEncoded());
		System.out.printf("\n key =[%s] \n",secretKey);
		
		//D92B253F1D22D87B0296F8DCE3D23FEF1FE9B7EC6AF05F2BC3D53EC66654F1D554AA6E585F084670AFB5C07DED5A1BABFC65CE2FCDDD5880F1A49C3255932394
	}
}
