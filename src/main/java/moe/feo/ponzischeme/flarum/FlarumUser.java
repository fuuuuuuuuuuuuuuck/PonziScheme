package moe.feo.ponzischeme.flarum;

public class FlarumUser {

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
        private String username;
        private String displayName;
        private String avatarUrl;
        private String slug;
        private String joinTime;
        private int discussionCount;
        private int commentCount;
        private boolean canEdit;
        private boolean canEditCredentials;
        private boolean canEditGroups;
        private boolean canDelete;
        private String lastSeenAt;
        private boolean isEmailConfirmed;
        private String email;
        private String markedAllAsReadAt;
        private int unreadNotificationCount;
        private int newNotificationCount;
        private String suspendMessage;
        private String suspendedUntil;
        private boolean canSuspend;
        private boolean blocksPd;
        private boolean cannotBeDirectMessaged;
        private int newFlagCount;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(String joinTime) {
            this.joinTime = joinTime;
        }

        public int getDiscussionCount() {
            return discussionCount;
        }

        public void setDiscussionCount(int discussionCount) {
            this.discussionCount = discussionCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public boolean isCanEditCredentials() {
            return canEditCredentials;
        }

        public void setCanEditCredentials(boolean canEditCredentials) {
            this.canEditCredentials = canEditCredentials;
        }

        public boolean isCanEditGroups() {
            return canEditGroups;
        }

        public void setCanEditGroups(boolean canEditGroups) {
            this.canEditGroups = canEditGroups;
        }

        public boolean isCanDelete() {
            return canDelete;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

        public String getLastSeenAt() {
            return lastSeenAt;
        }

        public void setLastSeenAt(String lastSeenAt) {
            this.lastSeenAt = lastSeenAt;
        }

        public boolean isEmailConfirmed() {
            return isEmailConfirmed;
        }

        public void setEmailConfirmed(boolean emailConfirmed) {
            isEmailConfirmed = emailConfirmed;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMarkedAllAsReadAt() {
            return markedAllAsReadAt;
        }

        public void setMarkedAllAsReadAt(String markedAllAsReadAt) {
            this.markedAllAsReadAt = markedAllAsReadAt;
        }

        public int getUnreadNotificationCount() {
            return unreadNotificationCount;
        }

        public void setUnreadNotificationCount(int unreadNotificationCount) {
            this.unreadNotificationCount = unreadNotificationCount;
        }

        public int getNewNotificationCount() {
            return newNotificationCount;
        }

        public void setNewNotificationCount(int newNotificationCount) {
            this.newNotificationCount = newNotificationCount;
        }

        public String getSuspendMessage() {
            return suspendMessage;
        }

        public void setSuspendMessage(String suspendMessage) {
            this.suspendMessage = suspendMessage;
        }

        public String getSuspendedUntil() {
            return suspendedUntil;
        }

        public void setSuspendedUntil(String suspendedUntil) {
            this.suspendedUntil = suspendedUntil;
        }

        public boolean isCanSuspend() {
            return canSuspend;
        }

        public void setCanSuspend(boolean canSuspend) {
            this.canSuspend = canSuspend;
        }

        public boolean isBlocksPd() {
            return blocksPd;
        }

        public void setBlocksPd(boolean blocksPd) {
            this.blocksPd = blocksPd;
        }

        public boolean isCannotBeDirectMessaged() {
            return cannotBeDirectMessaged;
        }

        public void setCannotBeDirectMessaged(boolean cannotBeDirectMessaged) {
            this.cannotBeDirectMessaged = cannotBeDirectMessaged;
        }

        public int getNewFlagCount() {
            return newFlagCount;
        }

        public void setNewFlagCount(int newFlagCount) {
            this.newFlagCount = newFlagCount;
        }
    }
}
