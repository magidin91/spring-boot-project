/* расширение для постгреса для хеширования  */
create extension if not exists pgcrypto;

/* хешируем уже добавленные пароли, gen_salt - генерир-ть соль, 'bf' (На базе Blowfish)- алгоритм хеширования, 8 - сложность */
update usr set password = crypt(password, gen_salt('bf', 8));