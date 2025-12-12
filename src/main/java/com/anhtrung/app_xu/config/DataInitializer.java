package com.anhtrung.app_xu.config;

import com.anhtrung.app_xu.domain.Blog;
import com.anhtrung.app_xu.domain.Category;
import com.anhtrung.app_xu.domain.WasteType;
import com.anhtrung.app_xu.repo.BlogRepository;
import com.anhtrung.app_xu.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;

    @Override
    public void run(String... args) {
        // Force update categories - xóa dữ liệu cũ và thêm mới
        categoryRepository.deleteAll();
        if (true) {

    // ===== KIM LOAI =====
    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Nhôm")
            .description("Chất thải nhôm là chất thải kim loại có thời gian phân hủy rất lâu do vật liệu phi sinh học. Nhôm được tạo ra qua khai thác quặng, tinh luyện, nấu chảy và đúc định hình.")
            .price(12000.0)
            .image("http://localhost:8083/images/nhom.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Sắt rắn")
            .description("Chất thải sắt rắn là chất thải kim loại bền, khó phân hủy trong môi trường vì là vật liệu phi sinh học. Sắt được tạo ra từ luyện quặng, nung chảy, tinh luyện và gia công.")
            .price(6000.0)
            .image("http://localhost:8083/images/sắt_rắn.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Lon nhôm")
            .description("Chất thải lon nhôm là chất thải kim loại có thời gian phân hủy rất dài do cấu tạo phi sinh học. Lon nhôm được tạo ra bằng nấu chảy nhôm, cán mỏng, tạo hình và phủ lớp bảo vệ.")
            .price(9000.0)
            .image("http://localhost:8083/images/lonnhom.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.KIM_LOAI)
            .notes("Đồng thau")
            .description("Chất thải đồng thau là chất thải hợp kim kim loại bền, phân hủy rất chậm vì là vật liệu phi sinh học. Đồng thau được tạo ra bằng nấu chảy đồng và kẽm, sau đó đúc và gia công.")
            .price(25000.0)
            .image("http://localhost:8083/images/dongthau.png")
            .build());

    // ===== DIEN TU =====
    categoryRepository.save(Category.builder()
            .type(WasteType.DIEN_TU)
            .notes("Màn hình LCD")
            .description("Chất thải màn hình LCD là chất thải điện tử khó phân hủy do gồm nhiều linh kiện phi sinh học. Màn hình LCD được tạo ra bằng quy trình lắp ráp tấm nền, lớp tinh thể lỏng, mạch điều khiển và đèn nền.")
            .price(30000.0)
            .image("http://localhost:8083/images/manhinhLCD.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.DIEN_TU)
            .notes("Màn hình hỏng")
            .description("Chất thải màn hình hỏng là chất thải điện tử có nhiều thành phần phi sinh học nên rất khó phân hủy. Màn hình được sản xuất bằng chế tạo tấm hiển thị, bo mạch, nguồn và lắp ráp, sau đó đóng khung hoàn thiện.")
            .price(20000.0)
            .image("http://localhost:8083/images/manhinhhong.png")
            .build());

    // ===== THUY TINH =====
    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Chai nguyên tử")
            .description("Chất thải chai nguyên tử là chất thải làm từ thủy tinh, phân hủy cực lâu do vật liệu phi sinh học. Chai/ống thủy tinh nhỏ được tạo ra qua nấu chảy, tạo hình và làm nguội.")
            .price(2000.0)
            .image("http://localhost:8083/images/chaithuytinh.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Chai thủy tinh")
            .description("Chất thải chai thủy tinh là chất thải làm từ thủy tinh có thời gian phân hủy lên tới 1 triệu năm. Điều này là do nhìn chung chất thải này được tạo ra bằng cách sử dụng các vật liệu phi sinh học. Chất thải từ chai thủy tinh được tạo ra thông qua quá trình nấu chảy và làm nguội.")
            .price(3000.0)
            .image("http://localhost:8083/images/chaithuytinh.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.THUY_TINH)
            .notes("Kính vỡ")
            .description("Chất thải kính vỡ là chất thải từ các tấm kính hoặc mảnh thủy tinh, có thời gian phân hủy rất lâu do vật liệu phi sinh học. Kính được tạo ra bằng nấu chảy thủy tinh, định hình thành tấm và làm nguội.")
            .price(1500.0)
            .image("http://localhost:8083/images/kinhvo.png")
            .build());

    // ===== GIAY =====
    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Sách")
            .description("Chất thải sách là chất thải giấy có nguồn gốc hữu cơ nên phân hủy nhanh hơn nhựa và thủy tinh. Sách được tạo ra từ nghiền bột giấy, xeo giấy, in ấn, đóng gáy và hoàn thiện thành phẩm.")
            .price(2500.0)
            .image("http://localhost:8083/images/sach.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Hộp bìa carton")
            .description("Chất thải hộp bìa carton là chất thải giấy bìa cứng, phân hủy tương đối nhanh do chủ yếu từ sợi cellulose. Carton được tạo ra bằng xeo giấy nhiều lớp, ép sóng, dán ghép, cắt gấp và tạo hình.")
            .price(3000.0)
            .image("http://localhost:8083/images/hopbiacatong.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Túi giấy")
            .description("Chất thải túi giấy là chất thải từ giấy có nguồn gốc hữu cơ nên có thể phân hủy sinh học nhanh hơn nhựa. Túi giấy được tạo ra bằng xeo giấy, cắt khuôn, dán mép, gấp tạo đáy và gia cố quai.")
            .price(2000.0)
            .image("http://localhost:8083/images/tuigiay.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.GIAY)
            .notes("Giấy ghi chú")
            .description("Chất thải giấy ghi chú là chất thải giấy mỏng, dễ phân hủy do làm từ sợi bột gỗ và phụ gia. Giấy ghi chú được tạo ra qua xeo giấy, cán mỏng, cắt kích thước, phủ keo dính và đóng thành tập.")
            .price(1800.0)
            .image("http://localhost:8083/images/giayghichu.png")
            .build());

    // ===== NHUA =====
    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Dầu ăn")
            .description("Chất thải dầu ăn đã qua sử dụng là chất thải hữu cơ dạng lỏng, khó xử lý nếu thải trực tiếp ra môi trường và có thể gây ô nhiễm nước. Dầu ăn được tạo ra từ ép hoặc tinh luyện dầu, sau đó dùng trong nấu nướng.")
            .price(1000.0)
            .image("http://localhost:8083/images/dauan.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Xô nhựa")
            .description("Chất thải xô nhựa là chất thải nhựa có thời gian phân hủy rất lâu do cấu tạo từ polymer phi sinh học. Xô nhựa được tạo ra bằng nung chảy hạt nhựa, ép khuôn hoặc ép phun, rồi làm nguội và hoàn thiện.")
            .price(3500.0)
            .image("http://localhost:8083/images/xonhua.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Chai nhựa")
            .description("Chất thải chai nhựa là chất thải polymer có thời gian phân hủy kéo dài hàng trăm năm vì là vật liệu phi sinh học. Chai nhựa được tạo ra bằng nung chảy hạt nhựa, thổi khuôn tạo hình, sau đó làm nguội và đóng nắp.")
            .price(3000.0)
            .image("http://localhost:8083/images/chainhua.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Cốc nhựa")
            .description("Chất thải cốc nhựa là chất thải từ nhựa tổng hợp, khó phân hủy trong tự nhiên do polymer phi sinh học. Cốc nhựa được tạo ra bằng nung chảy hạt nhựa, ép nhiệt hoặc ép phun, tạo hình và làm nguội.")
            .price(2000.0)
            .image("http://localhost:8083/images/cocnhua.png")
            .build());

    categoryRepository.save(Category.builder()
            .type(WasteType.NHUA)
            .notes("Nắp chai")
            .description("Chất thải nắp chai là chất thải nhựa nhỏ, phân hủy rất lâu do làm từ polymer phi sinh học như PP/HDPE. Nắp chai được tạo ra bằng ép phun nhựa nóng chảy vào khuôn, sau đó làm nguội và tạo ren khóa.")
            .price(2500.0)
            .image("http://localhost:8083/images/nnapchai.png")
            .build());

}
        // Thêm dữ liệu blog (xóa cũ và thêm mới)
        blogRepository.deleteAll();
        if (true) {
            blogRepository.save(Blog.builder()
                    .title("5 Lợi ích của việc tái chế rác thải điện tử")
                    .time(LocalDateTime.now().minusDays(5))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/doc-dao-y-tuong-bao-ve-moi-truong-tu-cac-mon-do-tai-che_1.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Hướng dẫn phân loại rác tại nhà hiệu quả")
                    .time(LocalDateTime.now().minusDays(3))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/4958_unnamed_2.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Tái chế nhựa - Bảo vệ môi trường xanh")
                    .time(LocalDateTime.now().minusDays(2))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/5042_c-trong-hoa-lop-xe-1-1542337607.jpg")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("Kinh nghiệm thu gom rác tái chế kiếm tiền")
                    .time(LocalDateTime.now().minusDays(1))
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/0523_trong-rau-sach-tai-nha-bang-chai-nhua.png")
                    .build());

            blogRepository.save(Blog.builder()
                    .title("GreenXu - Ứng dụng thu gom rác thông minh")
                    .time(LocalDateTime.now())
                    .image("https://ktmt.vnmediacdn.com/stores/news_dataimages/nguyenthiluan/032020/17/14/in_article/0354_cach-tai-che-chai-nhua.png")
                    .build());

        
        }
    }
}
