package com.anhtrung.app_xu.config;

import com.anhtrung.app_xu.domain.Blog;
import com.anhtrung.app_xu.domain.Category;
import com.anhtrung.app_xu.domain.Location;
import com.anhtrung.app_xu.domain.WasteType;
import com.anhtrung.app_xu.repo.BlogRepository;
import com.anhtrung.app_xu.repo.CategoryRepository;
import com.anhtrung.app_xu.repo.CartItemRepository;
import com.anhtrung.app_xu.repo.LocationRepository;
import com.anhtrung.app_xu.repo.WasteRequestItemRepository;
import com.anhtrung.app_xu.repo.WasteRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final CartItemRepository cartItemRepository;
    private final LocationRepository locationRepository;
    private final WasteRequestItemRepository wasteRequestItemRepository;
    private final WasteRequestRepository wasteRequestRepository;

    @Override
    public void run(String... args) {
        // Chỉ chạy khi database trống để tránh xóa data có sẵn
        if (categoryRepository.count() > 0) {
            System.out.println("⚠️ Database đã có data, bỏ qua việc khởi tạo");
            return;
        }
        
        // Xóa theo thứ tự để tránh foreign key constraint (chỉ khi database trống)
        wasteRequestItemRepository.deleteAll(); // Xóa items trước
        wasteRequestRepository.deleteAll(); // Xóa requests sau
        cartItemRepository.deleteAll();
        categoryRepository.deleteAll(); // Cuối cùng mới xóa categories
        if (true) { // Force chạy để update ảnh mới

    // ===== KIM LOAI =====
    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Nhôm")
            .description("Chất thải nhôm là chất thải kim loại có thời gian phân hủy rất lâu do vật liệu phi sinh học. Nhôm được tạo ra qua khai thác quặng, tinh luyện, nấu chảy và đúc định hình.")
            .price(12000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766651901/greenxu/bjtfh5paavpw1ar6k3xp.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Sắt rắn")
            .description("Chất thải sắt rắn là chất thải kim loại bền, khó phân hủy trong môi trường vì là vật liệu phi sinh học. Sắt được tạo ra từ luyện quặng, nung chảy, tinh luyện và gia công.")
            .price(6000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766651927/greenxu/nrjszfthhk5u9uyfatig.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Lon nhôm")
            .description("Chất thải lon nhôm là chất thải kim loại có thời gian phân hủy rất dài do cấu tạo phi sinh học. Lon nhôm được tạo ra bằng nấu chảy nhôm, cán mỏng, tạo hình và phủ lớp bảo vệ.")
            .price(9000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766651960/greenxu/hvsnqr5wv4lnvhuiasvm.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Đồng thau")
            .description("Chất thải đồng thau là chất thải hợp kim kim loại bền, phân hủy rất chậm vì là vật liệu phi sinh học. Đồng thau được tạo ra bằng nấu chảy đồng và kẽm, sau đó đúc và gia công.")
            .price(25000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766651984/greenxu/bz4koey6jc3kr2eknahr.png")
            .build());

    // ===== DIEN TU =====
    categoryRepository.save(Category.builder()
            .type(WasteType.DIEN_TU)
            .notes("Màn hình LCD")
            .description("Chất thải màn hình LCD là chất thải điện tử khó phân hủy do gồm nhiều linh kiện phi sinh học. Màn hình LCD được tạo ra bằng quy trình lắp ráp tấm nền, lớp tinh thể lỏng, mạch điều khiển và đèn nền.")
            .price(30000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652042/greenxu/uiwebmqntarzqyrdlifc.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.DIEN_TU)
            .notes("Màn hình hỏng")
            .description("Chất thải màn hình hỏng là chất thải điện tử có nhiều thành phần phi sinh học nên rất khó phân hủy. Màn hình được sản xuất bằng chế tạo tấm hiển thị, bo mạch, nguồn và lắp ráp, sau đó đóng khung hoàn thiện.")
            .price(20000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652013/greenxu/tutkrajusbjwe1ssep7p.png")
            .build());

    // ===== THUY TINH =====
    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Chai nguyên tử")
            .description("Chất thải chai nguyên tử là chất thải làm từ thủy tinh, phân hủy cực lâu do vật liệu phi sinh học. Chai/ống thủy tinh nhỏ được tạo ra qua nấu chảy, tạo hình và làm nguội.")
            .price(2000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652064/greenxu/uuk0sflhclhjune7pqzi.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Chai thủy tinh")
            .description("Chất thải chai thủy tinh là chất thải làm từ thủy tinh có thời gian phân hủy lên tới 1 triệu năm. Điều này là do nhìn chung chất thải này được tạo ra bằng cách sử dụng các vật liệu phi sinh học. Chất thải từ chai thủy tinh được tạo ra thông qua quá trình nấu chảy và làm nguội.")
            .price(3000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652089/greenxu/e2navr97flyeeapc2nq4.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Kính vỡ")
            .description("Chất thải kính vỡ là chất thải từ các tấm kính hoặc mảnh thủy tinh, có thời gian phân hủy rất lâu do vật liệu phi sinh học. Kính được tạo ra bằng nấu chảy thủy tinh, định hình thành tấm và làm nguội.")
            .price(1500.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652130/greenxu/amnwdzfifcaospj3ijat.png")
            .build());

    // ===== GIAY =====
    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Sách")
            .description("Chất thải sách là chất thải giấy có nguồn gốc hữu cơ nên phân hủy nhanh hơn nhựa và thủy tinh. Sách được tạo ra từ nghiền bột giấy, xeo giấy, in ấn, đóng gáy và hoàn thiện thành phẩm.")
            .price(2500.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652155/greenxu/wwz0wkphjtyqexpw3rxo.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Hộp bìa carton")
            .description("Chất thải hộp bìa carton là chất thải giấy bìa cứng, phân hủy tương đối nhanh do chủ yếu từ sợi cellulose. Carton được tạo ra bằng xeo giấy nhiều lớp, ép sóng, dán ghép, cắt gấp và tạo hình.")
            .price(3000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652174/greenxu/wkfpikefgkzgmho5ayqa.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Túi giấy")
            .description("Chất thải túi giấy là chất thải từ giấy có nguồn gốc hữu cơ nên có thể phân hủy sinh học nhanh hơn nhựa. Túi giấy được tạo ra bằng xeo giấy, cắt khuôn, dán mép, gấp tạo đáy và gia cố quai.")
            .price(2000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652197/greenxu/lafttr4hymg2njnfrl65.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Giấy ghi chú")
            .description("Chất thải giấy ghi chú là chất thải giấy mỏng, dễ phân hủy do làm từ sợi bột gỗ và phụ gia. Giấy ghi chú được tạo ra qua xeo giấy, cán mỏng, cắt kích thước, phủ keo dính và đóng thành tập.")
            .price(1800.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652216/greenxu/yjixgip7ojesvjqknbyv.png")
            .build());

    // ===== NHUA =====
    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Dầu ăn")
            .description("Chất thải dầu ăn đã qua sử dụng là chất thải hữu cơ dạng lỏng, khó xử lý nếu thải trực tiếp ra môi trường và có thể gây ô nhiễm nước. Dầu ăn được tạo ra từ ép hoặc tinh luyện dầu, sau đó dùng trong nấu nướng.")
            .price(1000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652234/greenxu/rkfn1svhnu3exztmrvaj.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Xô nhựa")
            .description("Chất thải xô nhựa là chất thải nhựa có thời gian phân hủy rất lâu do cấu tạo từ polymer phi sinh học. Xô nhựa được tạo ra bằng nung chảy hạt nhựa, ép khuôn hoặc ép phun, rồi làm nguội và hoàn thiện.")
            .price(3500.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652267/greenxu/g2wgxz0vwd9xubatmvt7.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Chai nhựa")
            .description("Chất thải chai nhựa là chất thải polymer có thời gian phân hủy kéo dài hàng trăm năm vì là vật liệu phi sinh học. Chai nhựa được tạo ra bằng nung chảy hạt nhựa, thổi khuôn tạo hình, sau đó làm nguội và đóng nắp.")
            .price(3000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652287/greenxu/q129kyuuoqiznsdus9a9.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Cốc nhựa")
            .description("Chất thải cốc nhựa là chất thải từ nhựa tổng hợp, khó phân hủy trong tự nhiên do polymer phi sinh học. Cốc nhựa được tạo ra bằng nung chảy hạt nhựa, ép nhiệt hoặc ép phun, tạo hình và làm nguội.")
            .price(2000.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652305/greenxu/lmvtph7r0e48n1fvd4ch.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Nắp chai")
            .description("Chất thải nắp chai là chất thải nhựa nhỏ, phân hủy rất lâu do làm từ polymer phi sinh học như PP/HDPE. Nắp chai được tạo ra bằng ép phun nhựa nóng chảy vào khuôn, sau đó làm nguội và tạo ren khóa.")
            .price(2500.0)
            .image("https://res.cloudinary.com/dmppisupw/image/upload/v1766652322/greenxu/qxp2po49xwidhed44nia.png")
            .build());

}
        // FORCE UPDATE blogs với ảnh mới
        blogRepository.deleteAll();
        if (true) { // Force chạy để update ảnh mới
            blogRepository.save(Blog.builder()
                    .title("5 Lợi ích của việc tái chế rác thải điện tử")
                    .description("Rác thải điện tử đang trở thành một vấn đề môi trường nghiêm trọng trên toàn thế giới. Việc tái chế rác thải điện tử không chỉ giúp bảo vệ môi trường mà còn mang lại nhiều lợi ích kinh tế và xã hội. Thứ nhất, tái chế giúp giảm thiểu ô nhiễm môi trường do các chất độc hại như chì, thủy ngân, cadmium có trong thiết bị điện tử. Thứ hai, việc tái chế giúp tiết kiệm tài nguyên thiên nhiên bằng cách tái sử dụng các kim loại quý như vàng, bạc, đồng. Thứ ba, tạo ra nhiều việc làm trong ngành công nghiệp tái chế. Thứ tư, giảm chi phí sản xuất thiết bị mới. Thứ năm, góp phần xây dựng nền kinh tế tuần hoàn bền vững. Để tham gia tái chế hiệu quả, người dân cần phân loại rác điện tử đúng cách, tìm hiểu các điểm thu gom uy tín và nâng cao ý thức bảo vệ môi trường. Mỗi hành động nhỏ của chúng ta đều góp phần tạo nên một tương lai xanh sạch đẹp hơn.")
                    .time(LocalDateTime.now().minusDays(5))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/doc-dao-y-tuong-bao-ve-moi-truong-tu-cac-mon-do-tai-che_1.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Hướng dẫn phân loại rác tại nhà hiệu quả")
                    .description("Phân loại rác tại nhà là bước đầu tiên và quan trọng nhất trong quá trình quản lý chất thải. Việc phân loại đúng cách không chỉ giúp giảm thiểu tác động tiêu cực đến môi trường mà còn tối ưu hóa quá trình tái chế. Đầu tiên, cần chuẩn bị các thùng rác riêng biệt cho từng loại chất thải: rác hữu cơ, rác tái chế, rác độc hại và rác thông thường. Rác hữu cơ bao gồm thức ăn thừa, vỏ trái cây, lá cây có thể phân hủy sinh học. Rác tái chế gồm giấy, nhựa, kim loại, thủy tinh cần được làm sạch trước khi bỏ vào thùng. Rác độc hại như pin, thuốc hết hạn, bóng đèn cần được xử lý riêng biệt. Để duy trì thói quen tốt, gia đình nên đặt thùng rác ở vị trí thuận tiện, dán nhãn rõ ràng và giáo dục tất cả thành viên về tầm quan trọng của việc phân loại rác. Ngoài ra, cần tìm hiểu lịch thu gom rác của địa phương để đưa rác đúng thời gian quy định.")
                    .time(LocalDateTime.now().minusDays(3))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/4958_unnamed_2.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Tái chế nhựa - Bảo vệ môi trường xanh")
                    .description("Nhựa là một trong những vật liệu gây ô nhiễm môi trường nghiêm trọng nhất hiện nay do thời gian phân hủy cực kỳ lâu, có thể lên đến hàng trăm năm. Tái chế nhựa là giải pháp hiệu quả để giảm thiểu tác động tiêu cực này. Quy trình tái chế nhựa bao gồm các bước: thu gom, phân loại theo mã số nhựa, làm sạch, nghiền nhỏ, nấu chảy và tạo hình thành sản phẩm mới. Các sản phẩm từ nhựa tái chế rất đa dạng như quần áo, thảm, đồ nội thất, vật liệu xây dựng. Để tham gia tái chế nhựa hiệu quả, người tiêu dùng cần học cách đọc mã số nhựa trên bao bì, làm sạch chai lọ trước khi vứt, tránh sử dụng nhựa một lần không cần thiết. Ngoài ra, nên ưu tiên mua sản phẩm từ nhựa tái chế để tạo động lực cho thị trường. Việc tái chế nhựa không chỉ giúp giảm rác thải mà còn tiết kiệm năng lượng, giảm phát thải khí nhà kính và bảo vệ hệ sinh thái biển khỏi ô nhiễm nhựa.")
                    .time(LocalDateTime.now().minusDays(2))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/5042_c-trong-hoa-lop-xe-1-1542337607.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Kinh nghiệm thu gom rác tái chế kiếm tiền")
                    .description("Thu gom rác tái chế không chỉ là hoạt động bảo vệ môi trường mà còn có thể trở thành nguồn thu nhập ổn định nếu biết cách tổ chức hiệu quả. Đầu tiên, cần tìm hiểu giá thu mua các loại vật liệu tái chế tại địa phương như giấy, nhựa, kim loại, thủy tinh. Giá cả thường dao động theo thị trường và mùa vụ. Tiếp theo, xây dựng mạng lưới thu gom từ gia đình, văn phòng, trường học, cửa hàng. Cần có phương tiện vận chuyển phù hợp và kho bãi tạm trữ. Kỹ năng phân loại và làm sạch vật liệu rất quan trọng để đạt giá cao nhất. Nên đầu tư cân điện tử để tính toán chính xác khối lượng. Xây dựng mối quan hệ tốt với các cơ sở thu mua để có giá ưu đãi. Ngoài ra, có thể mở rộng sang dịch vụ thu gom tại nhà với phí dịch vụ hợp lý. Hoạt động này vừa tạo thu nhập vừa góp phần bảo vệ môi trường, tạo việc làm cho cộng đồng và nâng cao ý thức về tái chế trong xã hội.")
                    .time(LocalDateTime.now().minusDays(1))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/0523_trong-rau-sach-tai-nha-bang-chai-nhua.png")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("GreenXu - Ứng dụng thu gom rác thông minh")
                    .description("GreenXu là ứng dụng di động tiên tiến được phát triển nhằm kết nối người dân với các dịch vụ thu gom rác tái chế một cách thông minh và hiệu quả. Ứng dụng cung cấp nhiều tính năng hữu ích như tra cứu thông tin các loại rác tái chế, tính toán giá trị thu mua, đặt lịch thu gom tại nhà, theo dõi điểm tích lũy và đổi quà. Người dùng chỉ cần chụp ảnh hoặc quét mã vạch sản phẩm để biết cách phân loại đúng. Hệ thống GPS tích hợp giúp tìm điểm thu gom gần nhất và tối ưu tuyến đường. Tính năng cộng đồng cho phép chia sẻ kinh nghiệm, tham gia các chiến dịch bảo vệ môi trường. Ứng dụng cũng cung cấp thống kê cá nhân về lượng rác đã tái chế, tiền kiếm được và tác động môi trường tích cực. Đối với doanh nghiệp, GreenXu cung cấp giải pháp quản lý chất thải toàn diện với báo cáo chi tiết. Với giao diện thân thiện, dễ sử dụng và tích hợp công nghệ AI, GreenXu đang góp phần xây dựng một cộng đồng ý thức cao về bảo vệ môi trường và phát triển bền vững.")
                    .time(LocalDateTime.now())
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/0354_cach-tai-che-chai-nhua.png")
                    .build());

        
        }

        // Thêm dữ liệu locations (chỉ khi chưa có)
        if (locationRepository.count() == 0) {
            locationRepository.save(Location.builder()
                    .name("Trung tâm Thu gom Rác Tái chế Đống Đa")
                    .address("123 Phố Huế, Đống Đa, Hà Nội")
                    .lat(21.0227)
                    .lng(105.8542)
                    .openTime("7:00 - 18:00 (Thứ 2 - Chủ nhật)")
                    .isActive(true)
                    .build());

            locationRepository.save(Location.builder()
                    .name("Điểm Thu gom GreenXu - Cầu Giấy")
                    .address("456 Nguyễn Trãi, Cầu Giấy, Hà Nội")
                    .lat(21.0285)
                    .lng(105.8048)
                    .openTime("6:30 - 19:00 (Hàng ngày)")
                    .isActive(true)
                    .build());

            locationRepository.save(Location.builder()
                    .name("Trạm Tái chế Hoàn Kiếm")
                    .address("789 Hàng Bài, Hoàn Kiếm, Hà Nội")
                    .lat(21.0245)
                    .lng(105.8412)
                    .openTime("8:00 - 17:30 (Thứ 2 - Thứ 7)")
                    .isActive(true)
                    .build());

            locationRepository.save(Location.builder()
                    .name("Kho Thu mua Rác Tái chế Thanh Xuân")
                    .address("321 Lê Văn Lương, Thanh Xuân, Hà Nội")
                    .lat(21.0058)
                    .lng(105.8019)
                    .openTime("7:30 - 18:30 (Thứ 2 - Chủ nhật)")
                    .isActive(true)
                    .build());

            locationRepository.save(Location.builder()
                    .name("Trung tâm Môi trường Xanh - Long Biên")
                    .address("654 Nguyễn Văn Cừ, Long Biên, Hà Nội")
                    .lat(21.0583)
                    .lng(105.8644)
                    .openTime("6:00 - 18:00 (Hàng ngày)")
                    .isActive(false) // Tạm đóng cửa
                    .build());

            System.out.println("✅ Đã thêm " + locationRepository.count() + " locations vào database");
        }
    }
}
