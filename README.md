# Authrize your app with the clientId and fetch repsonse code
curl -X GET https://accounts.spotify.com/authorize?client_id=93426d073dd343c39f96e422cb6cca16&response_type=code&redirect_uri=http://localhost:8888/callback&scope=user-read-private%20user-read-email&state=34fFs29kd09

# Retrive token from API with clientID:clientSecret ase base64 encoded in Authorization
# append as code the retrieved code
# as response you get a JSON object with the access-token
curl -X POST -H 'Content-Type: application/x-www-form-urlencoded' -H 'Authorization: Basic OTM0MjZkMDczZGQzNDNjMzlmOTZlNDIyY2I2Y2NhMTY6Y2Q2ZDFjZmMxZTE2NDQxZGJlOWMxYTM2MTQzZGVlZDI=' -d 'grant_type=authorization_code&code=AQAXfj3KT1WzhsT1chsjlTEaSOr-FKlUJLMZAuweOGfUnKbsYPl3zJ9Z3c2L7sQDsDwtOvAukSnHvQVwD6IYkRYGxcsSYjydLvs4Ch378a1NzleggeVrM5mzXebtnhSJWxNHnrcDsKh2MrycewYTOFHZ9GvwAKpNZC6mpN5Gy_WDpZ-21Nk1NDy1bdadCRAGS7aNewZ01HoZ-hO1jfkDQJT31soafQ&redirect_uri=http://localhost:8888/callback' 'https://accounts.spotify.com/api/token'

# JSON response
{"access_token":"BQCyAJ7J82Jdcp62vWZi9WkkZymV1KA-GXr8F3kjnOnrxSxumaqWQRG6ijkTzcE3L-acfgLHraLObKeC_nhRw8ijb22QtEbXfocARKhATtqt5lvR1CpXAMIdhtlCgaRYWGUxPL-RbwhpS-ocIjp3NpAmIfFL","token_type":"Bearer","expires_in":3600,"refresh_token":"AQCcbJIcV-p0G931F3zwBteE_6F2Mxv6M1yIzhK5fuZHCp7a8dxPtbxgQ-vJRm5fFbYTf1i_k7BNpxwlQl7AE4LwEl2s64-RNx23CiNEHleWaWxPHDeAwuKVX5N-mQfc-f4","scope":"user-read-email user-read-private"}
