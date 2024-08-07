package moe.feo.ponzischeme.flarum;

public class FlarumPost {

    private String type;
    private int id;
    private Attributes attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public class Attributes {

        private String number;
        private String createdAt;
        private String contentType;
        private String contentHtml;
        private boolean renderFailed;
        private String content;
        private String ipAddress;
        private boolean canEdit;
        private boolean canDelete;
        private boolean canHide;
        private boolean canFlag;
        private boolean isApproved;
        private boolean canApprove;
        private boolean canLike;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getContentHtml() {
            return contentHtml;
        }

        public void setContentHtml(String contentHtml) {
            this.contentHtml = contentHtml;
        }

        public boolean isRenderFailed() {
            return renderFailed;
        }

        public void setRenderFailed(boolean renderFailed) {
            this.renderFailed = renderFailed;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public boolean isCanDelete() {
            return canDelete;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

        public boolean isCanHide() {
            return canHide;
        }

        public void setCanHide(boolean canHide) {
            this.canHide = canHide;
        }

        public boolean isCanFlag() {
            return canFlag;
        }

        public void setCanFlag(boolean canFlag) {
            this.canFlag = canFlag;
        }

        public boolean isApproved() {
            return isApproved;
        }

        public void setApproved(boolean approved) {
            isApproved = approved;
        }

        public boolean isCanApprove() {
            return canApprove;
        }

        public void setCanApprove(boolean canApprove) {
            this.canApprove = canApprove;
        }

        public boolean isCanLike() {
            return canLike;
        }

        public void setCanLike(boolean canLike) {
            this.canLike = canLike;
        }
    }
}
