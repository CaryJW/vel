--调用的时候不超过阈值，则直接返回并执行计算器自加。

local key = KEYS[1]
-- 当前请求次数
local currentCount = redis.call('get', KEYS[1])
-- 限制请求次数
local limitCount = ARGV[1]
-- 时间范围
local limitPeriod = ARGV[2]

if currentCount and tonumber(currentCount) > tonumber(limitCount) then
    return currentCount;
end
currentCount = redis.call('incr', KEYS[1])
if tonumber(currentCount) == 1 then
    -- 设置过期时间
    redis.call('expire', key, limitPeriod)
end
return currentCount;