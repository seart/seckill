-- 秒杀执行存储过程
DELIMITER $

CREATE PROCEDURE seckill.execute_seckill
  (IN v_seckill_id BIGINT, IN v_phone BIGINT,
   IN v_kill_time  TIMESTAMP, OUT r_result INT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_killed (seckill_id, user_phone, create_time)
    VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT row_count()
    INTO insert_count;
    IF (insert_count = 0)
    THEN
      SET r_result = -1;
      ROLLBACK;
    ELSEIF (insert_count < 0)
      THEN
        SET r_result = -2;
        ROLLBACK;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = v_seckill_id
            AND end_time > v_kill_time
            AND start_time < v_kill_time
            AND number > 0;
      SELECT row_count()
      INTO insert_count;
      IF (insert_count = 0)
      THEN
        SET r_result = 0;
        ROLLBACK;
      ELSEIF (insert_count < 0)
        THEN
          SET r_result = -1;
          ROLLBACK;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$

DELIMITER ;

/*
-- 执行存储过程
set @r_result = -3;
call execute_seckill(1001, 15573342256, now(), @r_result);

-- 获取结果
select @r_result;
*/