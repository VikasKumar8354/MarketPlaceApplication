# Marketplace Backend (Spring Boot + JWT)

Requirements:
- Java 17
- Maven

Run:
mvn spring-boot:run

H2 console:
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:marketdb

Auth:
- POST /api/auth/register
  Body: { "name":"...", "email":"...", "password":"...", "role":"USER|VENDOR|ADMIN|SUPER_ADMIN", "shopName":"..." }
  Returns: { "token": "..." }

- POST /api/auth/login
  Body: { "email":"...", "password":"..." }
  Returns: { "token": "..." }

Use token for protected endpoints:
Authorization: Bearer <token>

Seeded users (from DataLoader):
- super@market.com / superpass  (SUPER_ADMIN)
- admin@market.com / adminpass  (ADMIN)
- vendor1@market.com / vendorpass (VENDOR)
- buyer@market.com / buyerpass (USER)

Sample flows:
- Login as vendor, create product: POST /api/products with Authorization header
- Super admin promote: POST /api/users/{id}/promote-admin (must be SUPER_ADMIN)
- Admin verify vendor: POST /api/users/{id}/verify-vendor (must be ADMIN or SUPER_ADMIN)

Notes:
- Replace jwt.secret in application.properties with a secure value for production.
- Passwords are stored hashed with BCrypt.
- Use role-based security via token claims; spring security maps claims to authorities in JwtFilter.
