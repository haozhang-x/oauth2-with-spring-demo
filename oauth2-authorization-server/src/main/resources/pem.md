ES256 使用 ECDSA 算法进行签名，该算法使用 ECC 密钥，生成命令如下：
# 1. 生成 ec 算法的私钥，使用 prime256v1 算法，密钥长度 256 位。（强度大于 2048 位的 RSA 密钥）
openssl ecparam -genkey -name prime256v1 -out ecc-private-key.pem
# 2. 通过密钥生成公钥
openssl ec -in ecc-private-key.pem -pubout -out ecc-public-key.pem
# 3. 转换私钥格式为pkcs8
openssl pkcs8 -topk8 -inform pem -in ecc-private-key.pem -outform pem -nocrypt -out ecc-private-key-pkcs8.pem



openssl genrsa -out rsa_private.key 2048

openssl rsa -in rsa_private.key -pubout -out rsa_public.key

openssl pkcs8 -topk8 -in rsa_private.key -nocrypt -out rsa_pkcs8_private.key