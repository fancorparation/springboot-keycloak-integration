1️⃣ Mamur (Admin) – Keycloak’dan token olmoqda

Mamur Keycloak’ga username/password orqali murojaat qiladi va access token oladi.

<img width="2178" height="1253" alt="MamurGetToken" src="https://github.com/user-attachments/assets/b0c53660-155b-4060-a5a3-ec0bce7a355d" />
2️⃣ Mamur – Token orqali server endpointiga kirmoqda

Olingan token Authorization: Bearer <token> orqali Spring Boot backend’iga yuboriladi va Admin endpointiga muvaffaqiyatli kiradi.

<img width="2148" height="1136" alt="MamurAccessToServer" src="https://github.com/user-attachments/assets/76dab23c-583c-4f11-bcc0-731db6b48026" />
3️⃣ User – Keycloak’dan token olmoqda

Oddiy foydalanuvchi (User roli) ham xuddi shunday tarzda Keycloak’dan access token oladi.

<img width="2147" height="1244" alt="UserGetToken" src="https://github.com/user-attachments/assets/ed1bacf1-4bf9-483c-bcb0-698eddf9bf14" />
4️⃣ User – Token orqali endpointga kirmoqda

User o‘z tokeni bilan faqat o‘ziga ruxsat berilgan endpointlarga kira oladi.

<img width="2133" height="908" alt="UserAccessToServer" src="https://github.com/user-attachments/assets/ddb3281f-137e-40a2-a5a9-d739d79c0c02" />
