USE LAB3_MaHoaKhoaCongKhai;
GO
SELECT TOP(50) Id,CreatedAt,Algorithm,Operation,InputLength,Success FROM dbo.History ORDER BY Id DESC;
GO
SELECT COUNT(*) AS SoBanGhi FROM dbo.History;
GO
